package com.jorotayo.fl_datatracker.ui.screens.dataForm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.usecase.DeleteFieldUseCase
import com.jorotayo.fl_datatracker.domain.usecase.DeletePresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetFieldsForPresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetPresetsUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SaveFieldUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SavePresetUseCase
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData
import com.jorotayo.fl_datatracker.ui.components.toasts.ToastMode
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.AddField
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
    private val deleteField: DeleteFieldUseCase
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
            AddField -> onAddField()
            ConfirmDeleteField -> onConfirmDeleteField()
            DismissDeleteDialog -> onDismissDeleteDialog()
            DismissDeletePresetDialog -> onDismissDeletePresetDialog()
            DismissToast -> onDismissToast()
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Handlers
    // ─────────────────────────────────────────────────────────────────────────

    private fun onSelectPreset(event: SelectPreset) {
        _state.update { it.copy(selectedPreset = event.preset) }
        loadFieldsForPreset(event.preset.presetId)
    }

    private fun onSavePreset(event: SavePreset) {
        viewModelScope.launch {
            try {
                val presets = getPresets()
                savePreset(event.name, presets)
                val updated = getPresets()
                val newPreset = updated.firstOrNull { it.presetName == event.name }
                _state.update { s ->
                    s.copy(
                        presets = updated,
                        selectedPreset = newPreset ?: s.selectedPreset,
                        fields = emptyList() // new preset always has no fields
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
                loadPresets()
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    private fun onDismissDeletePresetDialog() {
        _state.update { it.copy(presetToDelete = null, showDeletePresetDialog = false) }
    }

    /**
     * Single handler for all field property edits.
     * Applies [DataFormEvent.UpdateField] to the matching field in-place and persists via [saveField].
     */
    private fun onUpdateField(event: UpdateField) {
        val updated = applyUpdate(event.field, event.update)

        // Optimistic UI update
        _state.update { s ->
            s.copy(fields = s.fields.map { if (it.id == updated.id) updated else it })
        }

        viewModelScope.launch {
            try {
                // ✅ FIX: Convert DataFieldUi to DataField (ObjectBox entity)
                val presetId = _state.value.selectedPreset?.presetId ?: 0L
                val domainField = updated.toDataField(presetId)

                saveField(domainField)
            } catch (e: Exception) {
                // Rollback on error
                _state.update { s ->
                    s.copy(
                        fields = s.fields.map { if (it.id == event.field.id) event.field else it },
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    private fun onAddField() {
        // Navigate to field creation flow or show a dialog — wire to your nav here
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
                        )
                    )
                }
                return@launch
            }

            // Convert with the correct presetId
            val domainField = event.field.toDataField(presetId)

            // Use case returns Result — handle it directly, don't rely on try/catch
            saveField(domainField)
                .onSuccess {
                    loadFieldsForPreset(presetId)
                    _state.update {
                        it.copy(
                            toast = AppToastData(
                                message = "\"${event.field.name}\" field created.",
                                mode = ToastMode.INFO
                            )
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            toast = AppToastData(
                                message = error.message ?: "Failed to save field.",
                                mode = ToastMode.ERROR
                            )
                        )
                    }
                }
        }
    }


    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Applies a [FieldUpdate] to a [DataFieldUi] and returns the updated copy.
     * Keeping this pure makes it easy to test independently.
     */
    private fun applyUpdate(field: DataFieldUi, update: FieldUpdate): DataFieldUi =
        when (update) {
            is FieldUpdate.Hint -> field.copy(hint = update.value)
            is FieldUpdate.BooleanOptions -> field.copy(booleanOptions = update.options)
            is FieldUpdate.TristateOptions -> field.copy(tristateOptions = update.options)
            is FieldUpdate.Type -> field.copy(type = update.type)
            FieldUpdate.ToggleActive -> field.copy(isActive = !field.isActive)
        }

    private fun loadPresets() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val presets = getPresets()
                _state.update { s ->
                    s.copy(
                        isLoading = false,
                        presets = presets,
                        selectedPreset = s.selectedPreset ?: presets.firstOrNull()
                    )
                }
                _state.value.selectedPreset?.let { loadFieldsForPreset(it.presetId) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    private fun loadFieldsForPreset(presetId: Long) {
        viewModelScope.launch {
            try {
                val domainFields = getFields(presetId)
                val uiFields = domainFields.map { it.toDataFieldUi() }

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
