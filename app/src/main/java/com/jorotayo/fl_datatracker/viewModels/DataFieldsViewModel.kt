package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Setting_
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.BoxState
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldScreenState
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.NewDataFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import javax.inject.Inject

@HiltViewModel
class DataFieldsViewModel @Inject constructor(
) : ViewModel() {
/*
    private val _dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)
    val dataFieldsBox = mutableStateOf(_dataFieldsBox.all.toList())

    private var _settingBox = ObjectBox.get().boxFor(Setting::class.java)
    val settingBox = mutableStateOf(_settingBox.all.toList())

    private var _presetBox = ObjectBox.get().boxFor(Preset::class.java)
    val presetBox = mutableStateOf(_presetBox.all.toList())*/

    var dataFieldScreenState = mutableStateOf(DataFieldScreenState())
    var boxState = mutableStateOf(BoxState())

    var _dataFieldsBox = boxState.value._dataFieldsBox
    var dataFieldsBox = boxState.value.dataFieldsBox
    var _presetBox = boxState.value._presetBox
    var presetBox = boxState.value.presetBox
    var _settingBox = boxState.value._settingsBox
    var settingBox = boxState.value.settingsBox

    var currentPreset =
        mutableStateOf(_settingBox.query(Setting_.settingName.equal("currentPreset")).build()
            .findFirst())


//    private val maxChar = 30
//    private val maxHintChar = 60

    private val _newDataField = mutableStateOf(NewDataFieldState(
        currentPreset = presetBox.value.first { it.presetId == currentPreset.value?.Id }
    ))

    var newDataField: State<NewDataFieldState> = _newDataField


    private var dataField = DataField(dataFieldId = 0, presetId = 0)
/*
    private val _channel = Channel<DataFieldsChannel>()
    val channel = _channel.receiveAsFlow()*/

    fun onEvent(event: DataFieldEvent) {
        when (event) {
            is DataFieldEvent.ToggleAddNewDataField -> {
                dataFieldScreenState.value = dataFieldScreenState.value.copy(
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
                dataField = _dataFieldsBox.get(event.index)
                dataField.dataFieldType = event.value
                _dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.EditIsEnabled -> {
                dataField = _dataFieldsBox.get(event.index)
                dataField.isEnabled = !dataField.isEnabled
                _dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.EditFirstValue -> {
                dataField = _dataFieldsBox.get(event.index)
                dataField.first = event.value
                _dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.EditSecondValue -> {
                dataField = _dataFieldsBox.get(event.index)
                dataField.second = event.value
                _dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.EditThirdValue -> {
                dataField = _dataFieldsBox.get(event.index)
                dataField.third = event.value
                _dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.ConfirmDelete -> {
                // dataFieldsBox.value = event.value
                updateDataFields("remove", event.value.last())
            }
            is DataFieldEvent.RestoreDeletedField -> {
                updateDataFields("put", dataFieldScreenState.value.deletedDataField)
            }
            is DataFieldEvent.OpenDeleteDialog -> {
                dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isDeleteDialogVisible = if (!dataFieldScreenState.value.isDeleteDialogVisible.value) mutableStateOf(
                        true) else mutableStateOf(false)
                )
                dataFieldScreenState.value.deletedDataField = event.dataField
            }
            is DataFieldEvent.TogglePresetDialog -> {
                dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isAddPresetDialogVisible = if (!dataFieldScreenState.value.isAddPresetDialogVisible.value) mutableStateOf(
                        true) else mutableStateOf(false)
                )
            }
            is DataFieldEvent.SaveDataField -> {
                dataFieldsBox.value = event.value
                dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isAddDataFieldVisible = !dataFieldScreenState.value.isAddDataFieldVisible
                )
            }
            is DataFieldEvent.ChangePreset -> {
                currentPreset.value?.settingStringValue = event.value
            }
            is DataFieldEvent.AddPreset -> {
                val preset = Preset(
                    presetId = 0,
                    presetName = event.value
                )
                _presetBox.put(preset)
            }
        }
    }

    private fun updateDataFields(operation: String, data: DataField) {
        when (operation) {
            "put" -> {
                val _tempBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)
                _tempBox.put(data)
                val tempBox = mutableStateOf(_tempBox.all.toList())
                val newList = tempBox.value
                dataFieldsBox.value = newList
            }
            "remove" -> {
                val _tempBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)
                _tempBox.remove(data)
                val tempBox = mutableStateOf(_tempBox.all.toList())
                val newList = tempBox.value
                dataFieldsBox.value = newList
            }
        }
    }

    fun getDataField(itemIndex: Long): DataField {
        return _dataFieldsBox.get(itemIndex)
    }
}