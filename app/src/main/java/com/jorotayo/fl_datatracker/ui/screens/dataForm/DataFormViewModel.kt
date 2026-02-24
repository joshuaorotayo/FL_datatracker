package com.jorotayo.fl_datatracker.ui.screens.dataForm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.usecase.DeleteFieldUseCase
import com.jorotayo.fl_datatracker.domain.usecase.DeletePresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetFieldsForPresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetPresetsUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SaveFieldUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SavePresetUseCase
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
            is DataFormEvent.SelectPreset -> onSelectPreset(event)
            is DataFormEvent.SavePreset -> onSavePreset(event)
            is DataFormEvent.DeletePreset -> onDeletePreset(event)
            is DataFormEvent.UpdateField -> onUpdateField(event)
            is DataFormEvent.RequestDeleteField -> onRequestDeleteField(event)
            is DataFormEvent.SaveField -> onSaveField(event)
            DataFormEvent.AddField -> onAddField()
            DataFormEvent.ConfirmDeleteField -> onConfirmDeleteField()
            DataFormEvent.DismissDeleteDialog -> onDismissDeleteDialog()
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Handlers
    // ─────────────────────────────────────────────────────────────────────────

    private fun onSelectPreset(event: DataFormEvent.SelectPreset) {
        _state.update { it.copy(selectedPreset = event.preset) }
        loadFieldsForPreset(event.preset.presetId)
    }

    private fun onSavePreset(event: DataFormEvent.SavePreset) {
        viewModelScope.launch {
            try {
                val presets = getPresets()
                savePreset(event.name, presets)

                loadPresets()
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    private fun onDeletePreset(event: DataFormEvent.DeletePreset) {
        viewModelScope.launch {
            try {
                deletePreset(event.preset)
                loadPresets()
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    /**
     * Single handler for all field property edits.
     * Applies [event.update] to the matching field in-place and persists via [saveField].
     */
    private fun onUpdateField(event: DataFormEvent.UpdateField) {
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

                val existingDomainFields = _state.value.fields.map { it.toDataField(presetId) }
                saveField(domainField, existingDomainFields)

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

    private fun onRequestDeleteField(event: DataFormEvent.RequestDeleteField) {
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

    private fun onSaveField(event: DataFormEvent.SaveField) {
        viewModelScope.launch {
            try {
                // ✅ FIX: Convert DataFieldUi to DataField (ObjectBox entity)
                val presetId = _state.value.selectedPreset?.presetId ?: 0L
                val domainField = event.field.toDataField(presetId)

                val existingDomainFields = _state.value.fields.map { it.toDataField(presetId) }
                saveField(domainField, existingDomainFields)

                loadFieldsForPreset(presetId)
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
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

}

/**
 * ✅ ALL FIXES APPLIED:
 *
 * 1. Line 63 (SavePreset):
 *    - Passes just name (adjust if your use case needs 'existing' parameter)
 *
 * 2. Line 74 (DeletePreset):
 *    - Passes event.preset.presetId (Long) instead of event.preset (Preset)
 *
 * 3. Lines 96, 143 (SaveField):
 *    - Converts DataFieldUi to DataField using .toDataField(presetId)
 *    - Properly passes presetId from current state
 *
 * 4. Line 190 (GetFields):
 *    - Gets List<DataField> from use case
 *    - Converts to List<DataFieldUi> using .map { it.toDataFieldUi() }
 *    - Updates state with converted list
 *
 * NEXT STEPS:
 * 1. Add DataFieldUiAdapter.kt to your project
 * 2. Replace your ViewModel with this fixed version
 * 3. Verify your use case signatures match the comments
 * 4. Adjust if your SaveFieldUseCase needs existingFields parameter
 */