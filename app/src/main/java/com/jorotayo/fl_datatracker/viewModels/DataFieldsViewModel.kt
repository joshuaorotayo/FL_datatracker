package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.InvalidDataFieldException
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.useCases.DataFieldUseCases
import com.jorotayo.fl_datatracker.domain.useCases.PresetUseCases
import com.jorotayo.fl_datatracker.domain.useCases.SettingsUseCases
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldScreenState
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.NewDataFieldState
import com.jorotayo.fl_datatracker.util.exampleDataFieldList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataFieldsViewModel @Inject constructor(
    private val dataFieldUseCases: DataFieldUseCases,
    private val presetUseCases: PresetUseCases,
    private val settingsUseCases: SettingsUseCases,
) : ViewModel() {

    private var _selectedPresetName = "Default"
    private var selectedPresetName: MutableState<String> = mutableStateOf(_selectedPresetName)

    private var currentPreset =
        presetUseCases.getCurrentPresetFromSettings(selectedPresetName.value)
    private var newPresetList = presetUseCases.getPresetList()

    private var _dataFieldScreenState = mutableStateOf(
        DataFieldScreenState(
//            dataFields = dataFieldUseCases.getDataFieldsByPresetId(currentPreset.presetId),
            dataFields = exampleDataFieldList,
            presetList = newPresetList,
            currentPreset = presetUseCases.getCurrentPresetFromSettings(currentPreset.presetName)
        )
    )
    val dataFieldScreenState: State<DataFieldScreenState> = _dataFieldScreenState

    private val _newDataField =
        mutableStateOf(NewDataFieldState(currentPreset = currentPreset.presetName))
    var newDataField: MutableState<NewDataFieldState> = _newDataField

    private var dataField = DataField(dataFieldId = 0, presetId = 0)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onDataFieldEvent(event: DataFieldEvent) {
        when (event) {
            DataFieldEvent.RestoreDeletedField -> onRestoreDataField()
            DataFieldEvent.ToggleAddNewDataField -> onToggleAddNewDataField()
            DataFieldEvent.ToggleAddPresetDialog -> onToggleAddPresetDialog()
            DataFieldEvent.ToggleDeleteRowDialog -> onToggleDeleteRowDialog()
            DataFieldEvent.TogglePresetDeleteDialog -> onTogglePresetDeleteDialog()
            DataFieldEvent.ExpandPresetDropdown -> onExpandPresetDropdown()
            DataFieldEvent.HidePresetDropdown -> onHidePresetDropdown()
            is DataFieldEvent.AddFieldName -> onAddFieldName(event)
            is DataFieldEvent.AddHintText -> onAddHintText(event)
            is DataFieldEvent.AddFirstValue -> onAddFirstValue(event)
            is DataFieldEvent.AddSecondValue -> onAddSecondValue(event)
            is DataFieldEvent.AddThirdValue -> onAddThirdValue(event)
            is DataFieldEvent.SelectFieldType -> onSelectFieldType(event)
            is DataFieldEvent.DeleteDataField -> onDeleteDataField(event)
            is DataFieldEvent.OpenDeleteDialog -> onOpenDeleteDialog(event)
            is DataFieldEvent.SaveDataField -> onSaveDataField(event)
        }
    }

    private fun onOpenDeleteDialog(event: DataFieldEvent.OpenDeleteDialog) {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            isDeleteDialogVisible = _dataFieldScreenState.value.isDeleteDialogVisible.not()
        )
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            deletedDataField = event.dataField
        )
    }

    private fun onDeleteDataField(event: DataFieldEvent.DeleteDataField) {
        dataFieldUseCases.deleteDataField(event.value)
        updateDataFields()
    }

    private fun onAddFieldName(event: DataFieldEvent.AddFieldName) {
        if (event.value.length <= dataFieldScreenState.value.maxChar) {
            _newDataField.value = newDataField.value.copy(
                fieldName = event.value
            )
        }
    }

    private fun onAddFirstValue(event: DataFieldEvent.AddFirstValue) {
        _newDataField.value = newDataField.value.copy(
            firstValue = event.value
        )
    }

    private fun onAddSecondValue(event: DataFieldEvent.AddSecondValue) {
        _newDataField.value = newDataField.value.copy(
            secondValue = event.value
        )
    }

    private fun onAddThirdValue(event: DataFieldEvent.AddThirdValue) {
        _newDataField.value = newDataField.value.copy(
            thirdValue = event.value
        )
    }

    private fun onAddHintText(event: DataFieldEvent.AddHintText) {
        if (event.value.length <= dataFieldScreenState.value.maxHintChar) {
            _newDataField.value = newDataField.value.copy(
                fieldHint = event.value
            )
        }
    }

    private fun onRestoreDataField() {
        dataFieldUseCases.addDataField(dataFieldScreenState.value.deletedDataField)
        updateDataFields()
    }

    private fun onSaveDataField(event: DataFieldEvent.SaveDataField) {
        viewModelScope.launch {
            try {
                dataFieldUseCases.addDataField(event.value)
                _eventFlow.emit(UiEvent.SaveDataField)
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isAddDataFieldVisible = !dataFieldScreenState.value.isAddDataFieldVisible
                )
                updateDataFields()
            } catch (e: InvalidDataFieldException) {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        message = "Data Field Not Saved!"
                    )
                )
            }
        }
    }

    private fun onToggleDeleteRowDialog() {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            isDeleteDialogVisible = !_dataFieldScreenState.value.isDeleteDialogVisible
        )
    }

    private fun onExpandPresetDropdown(){
        _dataFieldScreenState.value = _dataFieldScreenState.value.copy(
            isPresetDropDownMenuExpanded = true
        )
    }

    private fun onHidePresetDropdown(){
        _dataFieldScreenState.value = _dataFieldScreenState.value.copy(
            isPresetDropDownMenuExpanded = false
        )
    }

    /*  private fun onTogglePresetDeleteDialog() {
          _dataFieldScreenState.value = dataFieldScreenState.value.copy(
              isPresetDeleteDialogVisible = !dataFieldScreenState.value.isPresetDeleteDialogVisible
          )
      }*/

    private fun onToggleAddNewDataField() {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            isAddDataFieldVisible = !dataFieldScreenState.value.isAddDataFieldVisible
        )
    }

    private fun updateDataFields() {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(currentPreset.presetId)
        )
    }

    private fun onSelectFieldType(event: DataFieldEvent.SelectFieldType) {
        _newDataField.value = newDataField.value.copy(
            fieldType = event.value
        )
    }

    fun onRowEvent(event: RowEvent) {
        when (event) {
            is RowEvent.EditFieldName -> onEditFieldName(event)
            is RowEvent.EditHintText -> onEditHintText(event)
            is RowEvent.EditRowType -> onEditRowType(event)
            is RowEvent.EditIsEnabled -> onEditIsEnabled(event)
            is RowEvent.EditFirstValue -> onEditFirstValue(event)
            is RowEvent.EditSecondValue -> onEditSecondValue(event)
            is RowEvent.EditThirdValue -> onEditThirdValue(event)
        }
        ObjectBox.get().boxFor(DataField::class.java).put(dataField)

        dataFieldUseCases.updateDataField(dataField)
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(currentPreset.presetId)
        )
    }

    private fun onEditFieldName(event: RowEvent.EditFieldName) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.fieldName = event.value
    }

    private fun onEditHintText(event: RowEvent.EditHintText) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.fieldHint = event.value
    }

    private fun onEditRowType(event: RowEvent.EditRowType) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.dataFieldType = event.value
    }

    private fun onEditIsEnabled(event: RowEvent.EditIsEnabled) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.isEnabled = !dataField.isEnabled
    }

    private fun onEditFirstValue(event: RowEvent.EditFirstValue) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.first = event.value
    }

    private fun onEditSecondValue(event: RowEvent.EditSecondValue) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.second = event.value
    }

    private fun onEditThirdValue(event: RowEvent.EditThirdValue) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.third = event.value
    }

    fun onPresetEvent(event: PresetEvent) {
        when (event) {
            is PresetEvent.ToggleAddPresetDialog -> onToggleAddPresetDialog()
            is PresetEvent.TogglePresetDeleteDialog -> onTogglePresetDeleteDialog()
            is PresetEvent.ChangePreset -> onChangePreset(event)
            is PresetEvent.AddPreset -> onAddPreset(event)
            is PresetEvent.DeletePreset -> onDeletePreset(event)
        }
    }

    private fun onToggleAddPresetDialog() {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            isPresetDropDownMenuExpanded = !_dataFieldScreenState.value.isPresetDropDownMenuExpanded,
            isAddPresetDialogVisible = !_dataFieldScreenState.value.isAddPresetDialogVisible
        )
    }

    private fun onTogglePresetDeleteDialog() {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            isPresetDeleteDialogVisible = !_dataFieldScreenState.value.isPresetDeleteDialogVisible
        )
    }

    private fun onChangePreset(event: PresetEvent.ChangePreset) {
        settingsUseCases.addSetting(
            Setting(
                settingId = 0,
                settingName = "currentPreset",
                settingBoolValue = false,
                settingStringValue = event.value
            )
        )

        val newPreset = presetUseCases.getPresetByPresetName(event.value)
        currentPreset = newPreset
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(newPreset.presetId),
            isPresetDropDownMenuExpanded = !_dataFieldScreenState.value.isPresetDropDownMenuExpanded,
            presetList = presetUseCases.getPresetList(),
            currentPreset = newPreset
        )
    }

    private fun onAddPreset(event: PresetEvent.AddPreset) {
        presetUseCases.addPreset(
            Preset(
                presetId = 0,
                presetName = event.value
            )
        )

        settingsUseCases.addSetting(
            Setting(
                settingId = 0,
                settingName = "currentPreset",
                settingBoolValue = false,
                settingStringValue = event.value
            )
        )

        val newPreset = presetUseCases.getPresetByPresetName(event.value)
        currentPreset = newPreset
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(newPreset.presetId),
            presetList = presetUseCases.getPresetList(),
            currentPreset = newPreset,
            isAddDataFieldVisible = !_dataFieldScreenState.value.isAddDataFieldVisible,
            isAddPresetDialogVisible = !_dataFieldScreenState.value.isAddPresetDialogVisible
        )
    }

    private fun onDeletePreset(event: PresetEvent.DeletePreset) {
        deletePresetActions(event.value)

        _dataFieldScreenState.value = _dataFieldScreenState.value.copy(
            isPresetDeleteDialogVisible = !_dataFieldScreenState.value.isPresetDeleteDialogVisible
        )
    }

    private fun deletePresetActions(preset: Preset) {
        val removeDataFields = dataFieldUseCases.getDataFieldsByPresetId(preset.presetId)

        dataFieldUseCases.deleteDataFields(removeDataFields)

        presetUseCases.deletePreset(preset)

        val newPreset = presetUseCases.getPresetByPresetName("Default")
        currentPreset = newPreset
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(newPreset.presetId),
            presetList = presetUseCases.getPresetList(),
            currentPreset = newPreset
        )
    }

    sealed class UiEvent {
        object SaveDataField : UiEvent()
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}