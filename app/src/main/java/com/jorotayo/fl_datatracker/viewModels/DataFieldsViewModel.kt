package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.BoxState
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldScreenState
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.NewDataFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataFieldsViewModel @Inject constructor(
) : ViewModel() {

    private val _dataFieldScreenState = mutableStateOf(DataFieldScreenState())
    val dataFieldScreenState: State<DataFieldScreenState> = _dataFieldScreenState

    private val _boxState = mutableStateOf(BoxState())
    val boxState: State<BoxState> = _boxState

    private val currentPresetName = boxState.value.currentPresetSetting?.settingStringValue

    private val _newDataField = mutableStateOf(NewDataFieldState(
        currentPreset = if (currentPresetName.isNullOrEmpty()) "Default" else currentPresetName
    ))

    var newDataField: State<NewDataFieldState> = _newDataField

    private var dataField = DataField(dataFieldId = 0, presetId = 0)

    fun onEvent(event: DataFieldEvent) {
        when (event) {
            is DataFieldEvent.ToggleAddNewDataField -> {
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isAddDataFieldVisible = !dataFieldScreenState.value.isAddDataFieldVisible
                )
            }
            is DataFieldEvent.AddFieldName -> {
                if (event.value.length <= dataFieldScreenState.value.maxChar) {
                    _newDataField.value = newDataField.value.copy(
                        fieldName = event.value
                    )
                }
            }
            is DataFieldEvent.SelectFieldType -> {
                _newDataField.value = newDataField.value.copy(
                    fieldType = event.value
                )
            }
            is DataFieldEvent.AddHintText -> {
                if (event.value.length <= dataFieldScreenState.value.maxHintChar) {
                    _newDataField.value = newDataField.value.copy(
                        fieldHint = event.value
                    )
                }
            }
            is DataFieldEvent.AddFirstValue -> {
                _newDataField.value = newDataField.value.copy(
                    firstValue = event.value
                )
            }
            is DataFieldEvent.AddSecondValue -> {
                _newDataField.value = newDataField.value.copy(
                    secondValue = event.value
                )
            }
            is DataFieldEvent.AddThirdValue -> {
                _newDataField.value = newDataField.value.copy(
                    thirdValue = event.value
                )
            }
            // Edit Operations
            is DataFieldEvent.EditFieldName -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.fieldName = event.value
                boxState.value._dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.EditHintText -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.fieldHint = event.value
                boxState.value._dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.EditRowType -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.dataFieldType = event.value
                boxState.value._dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.EditIsEnabled -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.isEnabled = !dataField.isEnabled
                boxState.value._dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.EditFirstValue -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.first = event.value
                boxState.value._dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.EditSecondValue -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.second = event.value
                boxState.value._dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.EditThirdValue -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.third = event.value
                boxState.value._dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.DeleteDataField -> {
                updateDataFields("remove", event.value)
            }
            is DataFieldEvent.RestoreDeletedField -> {
                updateDataFields("put", dataFieldScreenState.value.deletedDataField)
            }
            is DataFieldEvent.OpenDeleteDialog -> {
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isDeleteDialogVisible = if (!dataFieldScreenState.value.isDeleteDialogVisible.value) mutableStateOf(
                        true) else mutableStateOf(false)
                )
                dataFieldScreenState.value.deletedDataField = event.dataField
            }
            is DataFieldEvent.TogglePresetDialog -> {
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isAddPresetDialogVisible = if (!dataFieldScreenState.value.isAddPresetDialogVisible.value) mutableStateOf(
                        true) else mutableStateOf(false)
                )
            }
            is DataFieldEvent.SaveDataField -> {
                updateDataFields("put", event.value)
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isAddDataFieldVisible = !dataFieldScreenState.value.isAddDataFieldVisible
                )
            }
            is DataFieldEvent.ChangePreset -> {
                _boxState.value = boxState.value.copy(
                    currentPresetSetting = Setting(
                        Id = 1.toLong(),
                        settingName = "currentPreset",
                        settingStringValue = event.value
                    )
                )
            }
            is DataFieldEvent.AddPreset -> {
                val newPresetBox = ObjectBox.get().boxFor(Preset::class.java)
                newPresetBox.put(
                    Preset(
                        presetId = 0,
                        presetName = event.value
                    )
                )
                _boxState.value = boxState.value.copy(
                    presetBox = newPresetBox.all
                )

            }
        }
    }

    private fun updateDataFields(operation: String, dataField: DataField) {
        val newDataFieldBox = ObjectBox.get().boxFor(DataField::class.java)
        if (operation == "put") {
            newDataFieldBox.put(dataField)
        } else {
            newDataFieldBox.remove(dataField)
        }
        _boxState.value = boxState.value.copy(
            dataFieldsBox = newDataFieldBox.all
        )
    }

    fun getDataField(itemIndex: Long): DataField {
        return _boxState.value._dataFieldsBox.get(itemIndex)
    }
}