package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.model.Setting_
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldsChannel
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.DataFieldScreenState
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.NewDataFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class DataFieldsViewModel @Inject constructor(
) : ViewModel() {

    private val _dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)
    val dataFieldsBox = mutableStateOf(_dataFieldsBox.all.toList())

    private var _settingBox = mutableStateOf(ObjectBox.get().boxFor(Setting::class.java))
    val settingBox: State<Box<Setting>> = _settingBox

    private var _presetBox = mutableStateOf(ObjectBox.get().boxFor(Preset::class.java))
    val presetBox: State<Box<Preset>> = _presetBox

    val currentPreset =
        _settingBox.value.query(Setting_.settingName.equal("currentPreset")).build().findFirst()

    private val maxChar = 30
    private val maxHintChar = 60

    private val _newDataField = mutableStateOf(NewDataFieldState())
    var newDataField: State<NewDataFieldState> = _newDataField

    private val _deletedDataField = mutableStateOf(DataField(dataFieldId = 0, presetId = 0))
    var deletedDataField: State<DataField> = _deletedDataField

    val dataFieldScreenState = mutableStateOf(DataFieldScreenState())

    private var dataField = DataField(dataFieldId = 0, presetId = 0)

    private val _channel = Channel<DataFieldsChannel>()
    val channel = _channel.receiveAsFlow()

    fun onEvent(event: DataFieldEvent) {
        when (event) {
            is DataFieldEvent.ToggleAddNewDataField -> {
                dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isAddDataFieldVisible = !dataFieldScreenState.value.isAddDataFieldVisible
                )
            }
            is DataFieldEvent.AddFieldName -> {
                if (event.value.length <= maxChar) {
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
                if (event.value.length <= maxHintChar) {
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
                dataField = _dataFieldsBox.get(event.index)
                dataField.fieldName = event.value
                _dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.EditHintText -> {
                dataField = _dataFieldsBox.get(event.index)
                dataField.fieldHint = event.value
                _dataFieldsBox.put(dataField)
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
                _deletedDataField.value = event.dataField
                _dataFieldsBox.remove(deletedDataField.value)
                dataFieldsBox.value -= deletedDataField.value
            }
            is DataFieldEvent.RestoreDeletedField -> {
                dataFieldsBox.value += deletedDataField.value
            }
            is DataFieldEvent.OpenDeleteDialog -> {
                dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isDeleteDialogVisible = if (!dataFieldScreenState.value.isDeleteDialogVisible.value) mutableStateOf(
                        true) else mutableStateOf(false)
                )
                _deletedDataField.value = event.dataField
            }
            is DataFieldEvent.SaveDataField -> {
                AddDataFields(event.value)
            }
            is DataFieldEvent.ChangePreset -> {
                if (currentPreset != null) {
                    currentPreset.settingStringValue = event.value
                }
            }
        }
    }

    fun AddDataFields(data: DataField) {
        val _newBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)
        _newBox.put(data)
        val newBox: MutableList<DataField> = _newBox.all
        dataFieldsBox.value = newBox
    }

    fun getDataField(itemIndex: Long): DataField {
        return _dataFieldsBox.get(itemIndex)
    }
}