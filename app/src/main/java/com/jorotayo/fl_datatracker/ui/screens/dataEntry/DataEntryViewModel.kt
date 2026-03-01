package com.jorotayo.fl_datatracker.ui.screens.dataEntry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState
import com.jorotayo.fl_datatracker.domain.usecase.GetFieldsForPresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetPresetByIdUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetRecordWithEntriesUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetSelectedPresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SaveRecordUseCase
import com.jorotayo.fl_datatracker.domain.usecase.ValidateFieldEntryUseCase
import com.jorotayo.fl_datatracker.domain.usecase.ValidationException
import com.jorotayo.fl_datatracker.domain.util.toUiState
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData
import com.jorotayo.fl_datatracker.ui.components.toasts.ToastMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataEntryViewModel @Inject constructor(
    private val getSelectedPreset: GetSelectedPresetUseCase,
    private val getPresetById: GetPresetByIdUseCase,
    private val getFields: GetFieldsForPresetUseCase,
    private val getRecordWithEntries: GetRecordWithEntriesUseCase,
    private val saveRecord: SaveRecordUseCase,
    private val validateEntry: ValidateFieldEntryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DataEntryState())
    val state = _state.asStateFlow()

    fun onEvent(event: DataEntryEvent) {
        when (event) {
            DataEntryEvent.LoadFromPreference -> onLoadFromPreference()
            is DataEntryEvent.LoadRecord -> onLoadRecord(event.recordId)
            is DataEntryEvent.UpdateValue -> onUpdateValue(event.fieldId, event.value)
            DataEntryEvent.EnableEditing -> onEnableEditing()
            DataEntryEvent.Submit -> onSubmit()
            DataEntryEvent.Clear -> onClear()
            DataEntryEvent.DismissToast -> onDismissToast()
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // NEW record — resolve preset from DataStore preference
    // ─────────────────────────────────────────────────────────────────────────

    private fun onLoadFromPreference() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val preset = getSelectedPreset()

            if (preset == null) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        presetMissing = true,
                        toast = AppToastData(
                            message = "No preset selected. Please select a preset in Data Forms before creating a record.",
                            mode = ToastMode.ERROR
                        )
                    )
                }
                return@launch
            }

            val fields = getFields(preset.presetId).map { it.toUiState() }

            _state.update {
                it.copy(
                    isLoading = false,
                    mode = DataEntryMode.NEW,
                    preset = preset,
                    presetMissing = false,
                    fields = fields,
                    values = fields.defaultValues(),
                    errors = emptyMap(),
                    isReadOnly = false,
                    editingRecordId = null,
                    isSaved = false,
                    toast = null
                )
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EDIT record — opens read-only, unlocked by EnableEditing
    // ─────────────────────────────────────────────────────────────────────────

    private fun onLoadRecord(recordId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = getRecordWithEntries(recordId)

            if (result == null) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        toast = AppToastData(
                            message = "Record not found.",
                            mode = ToastMode.ERROR
                        )
                    )
                }
                return@launch
            }

            val (record, entries) = result

            // Check whether the preset that was used to create this record still exists
            val preset = getPresetById(record.presetId)
            val presetMissing = preset == null

            // Only load field definitions if the preset still exists — we need them
            // to render the typed composables. Without them we fall back to raw display.
            val fields = if (!presetMissing) {
                getFields(record.presetId).map { it.toUiState() }
            } else {
                emptyList()
            }

            val savedValues = entries.associate { it.dataFieldId to it.value }

            _state.update {
                it.copy(
                    isLoading = false,
                    mode = DataEntryMode.EDIT,
                    preset = preset,
                    presetMissing = presetMissing,
                    fields = fields,
                    values = savedValues,
                    errors = emptyMap(),
                    isReadOnly = true,
                    editingRecordId = recordId,
                    isSaved = false,
                    toast = if (presetMissing) AppToastData(
                        message = "The preset used to create this record was deleted. Editing is disabled, but your data is preserved.",
                        mode = ToastMode.ERROR
                    ) else null
                )
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Unlock editing
    // ─────────────────────────────────────────────────────────────────────────

    private fun onEnableEditing() {
        // Guard: if the preset is missing, the Edit button should be greyed out
        // in the UI but we double-check here in case it fires anyway.
        if (_state.value.presetMissing) {
            _state.update {
                it.copy(
                    toast = AppToastData(
                        message = "Cannot edit: the preset for this record no longer exists.",
                        mode = ToastMode.ERROR
                    )
                )
            }
            return
        }
        _state.update { it.copy(isReadOnly = false) }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Field value update
    // ─────────────────────────────────────────────────────────────────────────

    private fun onUpdateValue(fieldId: Long, value: String) {
        _state.update { s ->
            s.copy(
                values = s.values + (fieldId to value),
                errors = s.errors - fieldId   // clear error as soon as user edits
            )
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Submit
    // ─────────────────────────────────────────────────────────────────────────

    private fun onSubmit() {
        val s = _state.value
        val presetId = s.preset?.presetId ?: return

        viewModelScope.launch {
            saveRecord(presetId, s.fields, s.values)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isSaved = true,
                            toast = AppToastData(
                                message = "Record saved successfully.",
                                mode = ToastMode.INFO
                            )
                        )
                    }
                }
                .onFailure { error ->
                    if (error is ValidationException) {
                        _state.update { it.copy(errors = error.errors) }
                    } else {
                        _state.update {
                            it.copy(
                                toast = AppToastData(
                                    message = error.message ?: "Failed to save record.",
                                    mode = ToastMode.ERROR
                                )
                            )
                        }
                    }
                }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Clear / dismiss
    // ─────────────────────────────────────────────────────────────────────────

    private fun onClear() {
        _state.update { s ->
            s.copy(
                values = s.fields.defaultValues(),
                errors = emptyMap()
            )
        }
    }

    private fun onDismissToast() {
        _state.update { it.copy(toast = null) }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Seeds a default value for every field so [values] is never sparse.
     * Type-specific defaults mirror what each composable shows on first render.
     */
    private fun List<DataFieldUiState>.defaultValues(): Map<Long, String> =
        associate { field ->
            field.fieldId to when (field) {
                is DataFieldUiState.Boolean -> "false"
                is DataFieldUiState.Count -> field.min.toString()
                is DataFieldUiState.TriState -> "-1"
                else -> ""
            }
        }
}