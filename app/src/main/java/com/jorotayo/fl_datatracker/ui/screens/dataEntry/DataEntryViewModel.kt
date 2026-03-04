package com.jorotayo.fl_datatracker.ui.screens.dataEntry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState
import com.jorotayo.fl_datatracker.domain.usecase.GetFieldsForPresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetPresetByIdUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetRecordWithEntriesUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetSelectedPresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SaveRecordUseCase
import com.jorotayo.fl_datatracker.domain.usecase.ValidationException
import com.jorotayo.fl_datatracker.domain.util.toUiState
import com.jorotayo.fl_datatracker.navigation.NavCommand
import com.jorotayo.fl_datatracker.navigation.NavigationManager
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData
import com.jorotayo.fl_datatracker.ui.components.toasts.ToastMode
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.DataEntryEvent.Clear
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.DataEntryEvent.DismissToast
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.DataEntryEvent.EnableEditing
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.DataEntryEvent.LoadFromPreference
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.DataEntryEvent.LoadRecord
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.DataEntryEvent.NavigateToDataFields
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.DataEntryEvent.Submit
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.DataEntryEvent.UpdateRecordName
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.DataEntryEvent.UpdateValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val _state = MutableStateFlow(DataEntryState())
    val state = _state.asStateFlow()

    fun onEvent(event: DataEntryEvent) {
        when (event) {
            LoadFromPreference -> onLoadFromPreference()
            is LoadRecord -> onLoadRecord(event.recordId)
            is UpdateRecordName -> onUpdateRecordName(event.name)
            is UpdateValue -> onUpdateValue(event.fieldId, event.value)
            NavigateToDataFields -> onNavigateToDataFields()
            EnableEditing -> onEnableEditing()
            Submit -> onSubmit()
            Clear -> onClear()
            DismissToast -> onDismissToast()
        }
    }

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
                    recordName = "",
                    values = fields.defaultValues(),
                    errors = emptyMap(),
                    isReadOnly = fields.isEmpty(),
                    editingRecordId = null,
                    isSaved = false,
                    toast = null
                )
            }
        }
    }

    private fun onLoadRecord(recordId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = getRecordWithEntries(recordId)

            if (result == null) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        toast = AppToastData(message = "Record not found.", mode = ToastMode.ERROR)
                    )
                }
                return@launch
            }

            val (record, entries) = result
            val preset = getPresetById(record.presetId)
            val presetMissing = preset == null

            val fields = if (!presetMissing) {
                getFields(record.presetId).map { it.toUiState() }
            } else emptyList()

            val savedValues = entries.associate { it.dataFieldId to it.value }

            _state.update {
                it.copy(
                    isLoading = false,
                    mode = DataEntryMode.EDIT,
                    preset = preset,
                    presetMissing = presetMissing,
                    fields = fields,
                    recordName = record.title,
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

    private fun onEnableEditing() {
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

    private fun onUpdateRecordName(name: String) {
        _state.update { it.copy(recordName = name) }
    }

    private fun onUpdateValue(fieldId: Long, value: String) {
        _state.update { s ->
            s.copy(
                values = s.values + (fieldId to value),
                errors = s.errors - fieldId
            )
        }
    }

    private fun onNavigateToDataFields() {
        navigationManager.navigate(NavCommand.ToRoute(Screen.DataForm.route))
    }

    private fun onSubmit() {
        val s = _state.value
        val presetId = s.preset?.presetId ?: return

        viewModelScope.launch {
            saveRecord(
                presetId = presetId,
                fields = s.fields,
                values = s.values,
                recordName = s.recordName,
                recordId = s.editingRecordId
            )
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
                    delay(2000)
                    navigationManager.navigate(NavCommand.Back)
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

    private fun onClear() {
        _state.update { s ->
            s.copy(
                recordName = "",
                values = s.fields.defaultValues(),
                errors = emptyMap()
            )
        }
    }

    private fun onDismissToast() {
        _state.update { it.copy(toast = null) }
    }

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