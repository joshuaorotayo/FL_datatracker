package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.NewDataFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import javax.inject.Inject

@HiltViewModel
class DataFieldsViewModel @Inject constructor(
) : ViewModel() {

    private val _dataFieldsBox2 = mutableStateOf(ObjectBox.get().boxFor(DataField::class.java))
    var dataFieldsBox2: MutableState<Box<DataField>> = _dataFieldsBox2

    private val maxChar = 30
    private val maxHintChar = 60

    private val _newDataField = mutableStateOf(NewDataFieldState())
    var newDataField: State<NewDataFieldState> = _newDataField

    private val _openDeleteDialog = mutableStateOf(false)
    var openDeleteDialog: MutableState<Boolean> = _openDeleteDialog

    private val _deletedDataField = mutableStateOf(DataField(id = 0))
    var deletedDataField: State<DataField> = _deletedDataField

    private val _isAddDataFieldVisible = mutableStateOf(false)
    var isAddDataFieldVisible: MutableState<Boolean> = _isAddDataFieldVisible

    private val _isDeleteDialogVisible = mutableStateOf(false)
    var isDeleteDialogVisible: MutableState<Boolean> = _isDeleteDialogVisible


    fun onEvent(event: DataFieldEvent) {
        when (event) {
            DataFieldEvent.ToggleAddNewDataField -> {
                _isAddDataFieldVisible.value = !isAddDataFieldVisible.value
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
                val dataField = dataFieldsBox2.value.get(event.index)
                dataField.fieldName = event.value
                dataFieldsBox2.value.put(dataField)
            }
            is DataFieldEvent.EditHintText -> {
                val dataField = dataFieldsBox2.value.get(event.index)
                dataField.fieldHint = event.value
                dataFieldsBox2.value.put(dataField)
            }
            is DataFieldEvent.EditRowType -> {
                val dataField = dataFieldsBox2.value.get(event.index)
                dataField.dataFieldType = event.value
                dataFieldsBox2.value.put(dataField)
            }
            is DataFieldEvent.EditIsEnabled -> {
                val dataField = dataFieldsBox2.value.get(event.index)
                dataField.isEnabled = !dataField.isEnabled
                dataFieldsBox2.value.put(dataField)
            }
            is DataFieldEvent.EditStateValues -> {
                val dataField = dataFieldsBox2.value.get(event.index)
                val dataList = dataField.dataList.toMutableList()
                dataList[event.valIndex] = event.value
                dataField.dataList = dataList.toList()
                dataFieldsBox2.value.put(dataField)
            }
            is DataFieldEvent.ConfirmDelete -> {
                _deletedDataField.value = event.dataField
                //dataFieldsBox2.value.remove(deletedDataField.value)
            }
            DataFieldEvent.RestoreDeletedField -> {
                dataFieldsBox2.value.put(deletedDataField.value)
            }
            is DataFieldEvent.OpenDeleteDialog -> {
                _isDeleteDialogVisible.value = !isDeleteDialogVisible.value
                _deletedDataField.value = event.dataField
            }

        }
    }
}