package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.InvalidDataFieldException
import com.jorotayo.fl_datatracker.domain.model.InvalidPresetException
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.repository.AppRepository
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys.CURRENT_PRESET
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys.SHOW_DASHBOARD_NAV_BAR
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldsViewModel.UiEvent.*
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.ConfirmDeleteDataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.DismissDeleteDataFieldDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.DismissPresetDropdown
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.ExpandPresetDropdown
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.RestoreDeletedField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.SaveDataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.ShowDeleteDataFieldDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.ToggleAddNewDataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.ToggleMemberForm
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ChangePreset
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.DeletePreset
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.DismissAddPresetDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.DismissDeletePresetDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.EditPresetName
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.SaveNewPreset
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ShowAddPresetDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ShowDeletePresetDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditFieldName
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditFirstValue
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditHintText
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditIsEnabled
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditRowType
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditSecondValue
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditThirdValue
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.ToggleRow
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataFieldsViewModel @Inject constructor(
    private val repository: AppRepository,
    private val userPreferenceStore: UserPreferenceStore
) : ViewModel() {

    private val currentPresetName: MutableState<String> =
        mutableStateOf(userPreferenceStore.getString(CURRENT_PRESET) ?: "Default")
    private var currentPreset = repository.getPresetByPresetName(currentPresetName.value)

    private val _state = MutableStateFlow(
        DataFieldScreenState(
            presetList = repository.getPresetList(),
            currentPreset = currentPreset,
            dataFields = repository.getDataFieldsByPresetId(currentPreset.presetId)
        )
    )
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var dataField = DataField(dataFieldId = 0, presetId = 0)

    fun onDataFieldEvent(event: DataFieldEvent) {
        when (event) {
            is RestoreDeletedField -> onRestoreDataField()
            is ExpandPresetDropdown -> onExpandPresetDropdown()
            is DismissPresetDropdown -> onDismissPresetDropdown()
            is ToggleAddNewDataField -> onToggleAddNewDataField()
            is ToggleMemberForm -> onToggleMemberForm()
            is DismissDeleteDataFieldDialog -> onDismissDeleteDataFieldDialog()
            is ConfirmDeleteDataField -> onConfirmDeleteDataField()
            is ShowDeleteDataFieldDialog -> onShowDeleteDataFieldDialog(event)
            is SaveDataField -> onSaveDataField(event)
        }
    }

    init {
        val presetList = repository.getPresetList()
        if (presetList.isEmpty()) {
            repository.addPreset(
                Preset(
                    presetId = 0,
                    presetName = "Default"
                )
            )

            userPreferenceStore.setString(Pair(CURRENT_PRESET, "Default"))
        }

        _state.value = state.value.copy(
            presetList = repository.getPresetList(),
            currentPreset = currentPreset,
            dataFields = repository.getDataFieldsByPresetId(currentPreset.presetId)
        )
    }

    private fun onDismissDeleteDataFieldDialog() {
        _state.value = state.value.copy(
            showDeleteDataFieldDialog = false
        )
    }

    private fun onShowDeleteDataFieldDialog(event: ShowDeleteDataFieldDialog) {
        _state.value = state.value.copy(
            showDeleteDataFieldDialog = true,
            currentDataField = event.value
        )
    }

    private fun onConfirmDeleteDataField() {
        _state.value = state.value.copy(
            showDeleteDataFieldDialog = false
        )
        state.value.currentDataField?.let { repository.deleteDataField(it) }
        updateDataFields()
    }

    private fun onRestoreDataField() {
        if (state.value.deletedDataField != null) {
            repository.addDataField(state.value.deletedDataField!!)
            updateDataFields()
        }
    }

    private fun onSaveDataField(event: SaveDataField) {
        viewModelScope.launch {
            try {
                repository.addDataField(event.value)
                _eventFlow.emit(SaveDataField("Data Field Saved: ${event.value.fieldName}"))
                _state.value = state.value.copy(
                    isAddDataFieldVisible = !state.value.isAddDataFieldVisible
                )
                updateDataFields()
            } catch (e: InvalidDataFieldException) {
                _eventFlow.emit(
                    ShowSnackbar(
                        message = e.message ?: "DataField Not saved"
                    )
                )
            }
        }
    }

    private fun onExpandPresetDropdown() {
        _state.value = state.value.copy(
            isPresetDropDownMenuExpanded = true
        )
    }

    private fun onDismissPresetDropdown() {
        _state.value = state.value.copy(
            isPresetDropDownMenuExpanded = false
        )
    }

    private fun onToggleAddNewDataField() {
        _state.value = state.value.copy(
            isAddDataFieldVisible = !state.value.isAddDataFieldVisible
        )
    }

    private fun onToggleMemberForm() {
        if (!state.value.isMemberFormVisible) {
            userPreferenceStore.setBoolean(Pair(SHOW_DASHBOARD_NAV_BAR, true))
        } else {
            userPreferenceStore.setBoolean(Pair(SHOW_DASHBOARD_NAV_BAR, false))
        }
        _state.value = state.value.copy(
            isMemberFormVisible = !state.value.isMemberFormVisible
        )
    }

    private fun updateDataFields() {
        _state.value = state.value.copy(
            dataFields = repository.getDataFieldsByPresetId(currentPreset.presetId)
        )
    }

    private fun updatePresetList() {
        _state.value = state.value.copy(
            presetList = repository.getPresetList()
        )
    }

    fun onRowEvent(event: RowEvent) {
        when (event) {
            is EditFieldName -> onEditFieldName(event)
            is EditHintText -> onEditHintText(event)
            is EditRowType -> onEditRowType(event)
            is EditIsEnabled -> onEditIsEnabled(event)
            is EditFirstValue -> onEditFirstValue(event)
            is EditSecondValue -> onEditSecondValue(event)
            is EditThirdValue -> onEditThirdValue(event)
            is ToggleRow -> onRowToggle(event)
        }
        ObjectBox.boxStore().boxFor(DataField::class.java).put(dataField)

        repository.updateDataField(dataField)
        _state.value = state.value.copy(
            dataFields = repository.getDataFieldsByPresetId(currentPreset.presetId)
        )
    }

    private fun onRowToggle(event: ToggleRow) {
        dataField =
            state.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.isEnabled = !dataField.isEnabled
    }

    private fun onEditFieldName(event: EditFieldName) {
        dataField =
            state.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.fieldName = event.value
    }

    private fun onEditHintText(event: EditHintText) {
        dataField =
            state.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.fieldHint = event.value
    }

    private fun onEditRowType(event: EditRowType) {
        dataField =
            state.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.dataFieldType = DataFieldType.getByValue(event.value)
    }

    private fun onEditIsEnabled(event: EditIsEnabled) {
        dataField =
            state.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.isEnabled = !dataField.isEnabled
    }

    private fun onEditFirstValue(event: EditFirstValue) {
        dataField =
            state.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.first = event.value
    }

    private fun onEditSecondValue(event: EditSecondValue) {
        dataField =
            state.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.second = event.value
    }

    private fun onEditThirdValue(event: EditThirdValue) {
        dataField =
            state.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.third = event.value
    }

    fun onPresetEvent(event: PresetEvent) {
        when (event) {
            is ShowAddPresetDialog -> onShowAddPresetDialog()
            is ShowDeletePresetDialog -> onShowDeletePresetDialog(event)
            is DismissDeletePresetDialog -> onDismissDeletePresetDialog()
            is ChangePreset -> onChangePreset(event)
            is EditPresetName -> onEditPresetName(event)
            is DeletePreset -> onDeletePreset()
            is SaveNewPreset -> onSaveNewPreset()
            is DismissAddPresetDialog -> onDismissAddPresetDialog()
        }
    }

    private fun onDismissAddPresetDialog() {
        _state.value = state.value.copy(
            showAddPresetDialog = false
        )
    }

    private fun onEditPresetName(event: EditPresetName) {
        val newPreset = Preset(0, event.value)
        _state.value = state.value.copy(
            newPreset = newPreset
        )
    }

    private fun onShowAddPresetDialog() {
        _state.value = state.value.copy(
            isPresetDropDownMenuExpanded = false,
            newPreset = null,
            showAddPresetDialog = true
        )
    }

    private fun onSaveNewPreset() {
        Log.i("Add Preset", "saveNewPreset: Add Preset clicked")
        val newPreset = _state.value.newPreset
        viewModelScope.launch {
            if (newPreset == null || newPreset.presetName.isBlank()) {
                _state.value = state.value.copy(
                    textFieldError = true
                )
            } else {
                try {
                    repository.addPreset(newPreset)
                    val presets = repository.getPresetList()
                    val currentPreset = repository.getPresetByPresetName(newPreset.presetName)
                    _state.value = state.value.copy(
                        showAddPresetDialog = false,
                        isPresetDropDownMenuExpanded = false,
                        isAddDataFieldVisible = false,
                        presetList = presets,
                        currentPreset = newPreset,
                        dataFields = repository.getDataFieldsByPresetId(currentPreset.presetId)
                    )
                    userPreferenceStore.setString(Pair(CURRENT_PRESET, newPreset.presetName))
                    _eventFlow.emit(ShowSnackbar("Preset: ${newPreset.presetName} added!"))
                } catch (e: InvalidPresetException) {
                    _state.value = state.value.copy(
                        showAddPresetDialog = false,
                        isPresetDropDownMenuExpanded = false,
                        isAddDataFieldVisible = false
                    )
                    _eventFlow.emit(
                        ShowSnackbar(message = e.message!!)
                    )
                }
            }
        }
    }

    private fun onShowDeletePresetDialog(event: ShowDeletePresetDialog) {
        _state.value = state.value.copy(
            showDeletePresetDialog = true,
            isPresetDropDownMenuExpanded = false,
            modifiedPreset = event.value
        )
    }

    private fun onDismissDeletePresetDialog() {
        _state.value = state.value.copy(
            showDeletePresetDialog = false
        )
    }

    private fun onChangePreset(event: ChangePreset) {
        val newPreset = repository.getPresetByPresetName(event.value)

        userPreferenceStore.setString(Pair(CURRENT_PRESET, event.value))

        currentPreset = newPreset
        _state.value = state.value.copy(
            dataFields = repository.getDataFieldsByPresetId(newPreset.presetId),
            isPresetDropDownMenuExpanded = false,
            isAddDataFieldVisible = false,
            presetList = repository.getPresetList(),
            currentPreset = newPreset
        )
    }

    private fun onDeletePreset() {
        _state.value.modifiedPreset?.let { deletePresetActions(it) }
    }

    private fun deletePresetActions(preset: Preset) {
        val removeDataFields = repository.getDataFieldsByPresetId(preset.presetId)

        repository.deleteDataFields(removeDataFields)

        repository.deletePreset(preset)

        val newPreset = repository.getPresetByPresetName("Default")
        currentPreset = newPreset
        _state.value = state.value.copy(
            dataFields = repository.getDataFieldsByPresetId(newPreset.presetId),
            presetList = repository.getPresetList(),
            currentPreset = newPreset,
            isAddDataFieldVisible = false,
            isPresetDropDownMenuExpanded = false
        )
    }

    sealed class UiEvent {
        data class SaveDataField(val message: String) : UiEvent()
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}
