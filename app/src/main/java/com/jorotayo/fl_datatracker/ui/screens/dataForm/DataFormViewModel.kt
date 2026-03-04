package com.jorotayo.fl_datatracker.ui.screens.dataForm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.usecase.DeleteFieldUseCase
import com.jorotayo.fl_datatracker.domain.usecase.DeletePresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetFieldsForPresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetPresetsUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SaveFieldUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SavePresetUseCase
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData
import com.jorotayo.fl_datatracker.ui.components.toasts.ToastMode
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.ConfirmDeleteField
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.ConfirmDeletePreset
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.DeletePreset
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.DismissDeleteDialog
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.DismissDeletePresetDialog
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.DismissToast
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.RequestDeleteField
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.SaveField
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.SavePreset
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.SelectPreset
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.ToggleAddFieldSheet
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.UpdateField
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.DataFieldUi
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.FieldUpdate
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.toDataField
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.toDataFieldUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataFormViewModel @Inject constructor(
    private val getPresets: GetPresetsUseCase,
    private val savePreset: SavePresetUseCase,
    private val deletePreset: DeletePresetUseCase,
    private val getFields: GetFieldsForPresetUseCase,
    private val saveField: SaveFieldUseCase,
    private val deleteField: DeleteFieldUseCase,
    private val preferenceStore: UserPreferenceStore   // ← injected to persist selection
) : ViewModel() {

    private val _state = MutableStateFlow(DataFormState())
    val state = _state.asStateFlow()

    init {
        loadPresets()
    }

    fun onEvent(event: DataFormEvent) {
        when (event) {
            is SelectPreset -> onSelectPreset(event)
            is SavePreset -> onSavePreset(event)
            is DeletePreset -> onDeletePreset(event)
            is ConfirmDeletePreset -> onConfirmDeletePreset(event)
            is UpdateField -> onUpdateField(event)
            is RequestDeleteField -> onRequestDeleteField(event)
            is SaveField -> onSaveField(event)
            ToggleAddFieldSheet -> onToggleAddFieldSheet()
            ConfirmDeleteField -> onConfirmDeleteField()
            DismissDeleteDialog -> onDismissDeleteDialog()
            DismissDeletePresetDialog -> onDismissDeletePresetDialog()
            DismissToast -> onDismissToast()
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Preset selection — persists to DataStore so HomeScreen can read it
    // ─────────────────────────────────────────────────────────────────────────

    private fun onSelectPreset(event: SelectPreset) {
        _state.update { it.copy(selectedPreset = event.preset) }
        loadFieldsForPreset(event.preset.presetId)

        // Persist the selection so GetSelectedPresetUseCase can read it
        viewModelScope.launch {
            preferenceStore.setString(
                SettingsKeys.CURRENT_PRESET to event.preset.presetId.toString()
            )
        }
    }

    private fun onSavePreset(event: SavePreset) {
        viewModelScope.launch {
            try {
                val presets = getPresets()
                savePreset(event.name, presets)
                val updated = getPresets()
                val newPreset = updated.firstOrNull { it.presetName == event.name }

                // Persist the newly created preset as the current selection
                newPreset?.let {
                    preferenceStore.setString(
                        SettingsKeys.CURRENT_PRESET to it.presetId.toString()
                    )
                }

                _state.update { s ->
                    s.copy(
                        presets = updated,
                        selectedPreset = newPreset ?: s.selectedPreset,
                        fields = emptyList()
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    private fun onDeletePreset(event: DeletePreset) {
        _state.update {
            it.copy(presetToDelete = event.preset, showDeletePresetDialog = true)
        }
    }

    private fun onConfirmDeletePreset(event: ConfirmDeletePreset) {
        viewModelScope.launch {
            try {
                deletePreset(event.preset)
                _state.update { it.copy(presetToDelete = null, showDeletePresetDialog = false) }
                // loadPresets will re-select the first available preset (Default)
                // and persist it, so CURRENT_PRESET stays valid after deletion
                loadPresets()
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    private fun onDismissDeletePresetDialog() {
        _state.update { it.copy(presetToDelete = null, showDeletePresetDialog = false) }
    }

    private fun onUpdateField(event: UpdateField) {
        val updated = applyUpdate(event.field, event.update)
        _state.update { s ->
            s.copy(fields = s.fields.map { if (it.id == updated.id) updated else it })
        }
        viewModelScope.launch {
            try {
                val presetId = _state.value.selectedPreset?.presetId ?: 0L
                saveField(updated.toDataField(presetId))
            } catch (e: Exception) {
                _state.update { s ->
                    s.copy(
                        fields = s.fields.map { if (it.id == event.field.id) event.field else it },
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    private fun onToggleAddFieldSheet() {
        _state.update { it.copy(showAddFieldSheet = !it.showAddFieldSheet) }
    }

    private fun onRequestDeleteField(event: RequestDeleteField) {
        _state.update { it.copy(fieldToDelete = event.field, showDeleteFieldDialog = true) }
    }

    private fun onConfirmDeleteField() {
        val field = _state.value.fieldToDelete ?: return
        _state.update { s ->
            s.copy(
                fields = s.fields.filterNot { it.id == field.id },
                fieldToDelete = null,
                showDeleteFieldDialog = false
            )
        }
        viewModelScope.launch {
            try {
                deleteField(field.id)
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
                loadFieldsForPreset(_state.value.selectedPreset?.presetId ?: return@launch)
            }
        }
    }

    private fun onDismissDeleteDialog() {
        _state.update { it.copy(fieldToDelete = null, showDeleteFieldDialog = false) }
    }

    private fun onSaveField(event: SaveField) {
        viewModelScope.launch {
            val presetId = _state.value.selectedPreset?.presetId ?: run {
                _state.update {
                    it.copy(
                        toast = AppToastData(
                            message = "No preset selected.",
                            mode = ToastMode.ERROR
                        ),
                        showAddFieldSheet = !it.showAddFieldSheet
                    )
                }
                return@launch
            }
            saveField(event.field.toDataField(presetId))
                .onSuccess {
                    loadFieldsForPreset(presetId)
                    _state.update {
                        it.copy(
                            toast = AppToastData(
                                message = "\"${event.field.name}\" field created.",
                                mode = ToastMode.INFO
                            ),
                            showAddFieldSheet = !it.showAddFieldSheet
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            toast = AppToastData(
                                message = error.message ?: "Failed to save field.",
                                mode = ToastMode.ERROR
                            ),
                            showAddFieldSheet = !it.showAddFieldSheet
                        )
                    }
                }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    private fun applyUpdate(field: DataFieldUi, update: FieldUpdate): DataFieldUi =
        when (update) {
            is FieldUpdate.Hint -> field.copy(hint = update.value)
            is FieldUpdate.BooleanOptions -> field.copy(booleanOptions = update.options)
            is FieldUpdate.TristateOptions -> field.copy(tristateOptions = update.options)
            is FieldUpdate.Type -> field.copy(type = update.type)
            FieldUpdate.ToggleActive -> field.copy(isActive = !field.isActive)
        }

    /**
     * Loads all presets, restores or seeds the current selection, and persists
     * the resolved id back to DataStore so it is always valid after this call.
     */
    private fun loadPresets() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val presets = getPresets()
                val currentPresetId =
                    preferenceStore.getString(SettingsKeys.CURRENT_PRESET).toLongOrNull()

                // Prefer the stored selection; fall back to the first preset (Default)
                val selected = presets.firstOrNull { it.presetId == currentPresetId }
                    ?: presets.firstOrNull()

                // Persist whichever preset we resolved so the store is always fresh
                selected?.let {
                    preferenceStore.setString(
                        SettingsKeys.CURRENT_PRESET to it.presetId.toString()
                    )
                }

                _state.update { s ->
                    s.copy(
                        isLoading = false,
                        presets = presets,
                        selectedPreset = selected
                    )
                }

                selected?.let { loadFieldsForPreset(it.presetId) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    private fun loadFieldsForPreset(presetId: Long) {
        viewModelScope.launch {
            try {
                val uiFields = getFields(presetId).map { it.toDataFieldUi() }
                _state.update { it.copy(fields = uiFields) }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    private fun onDismissToast() {
        _state.update { it.copy(toast = null) }
    }
}