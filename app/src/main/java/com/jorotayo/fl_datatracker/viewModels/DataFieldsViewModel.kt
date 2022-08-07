package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.InvalidDataFieldException
import com.jorotayo.fl_datatracker.domain.util.use_case.AddDataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.DataFieldScreenState
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.NewDataFieldState
import com.jorotayo.fl_datatracker.util.capitaliseWord
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.reactive.DataObserver
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataFieldsViewModel @Inject constructor(
    private val addDataField: AddDataField,
) : ViewModel() {

    val dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)

    val observer = DataObserver<Class<DataField>> {
        val results: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)
    }

    private val maxChar = 30

    private val _isAddDataFieldVisible = mutableStateOf(DataFieldScreenState())
    var isAddDataFieldVisible: State<DataFieldScreenState> = _isAddDataFieldVisible

    private val _newDataField = mutableStateOf(NewDataFieldState())
    var newDataField: State<NewDataFieldState> = _newDataField

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: DataFieldEvent) {
        when (event) {
            DataFieldEvent.ToggleAddNewDataField -> {
                _isAddDataFieldVisible.value = _isAddDataFieldVisible.value.copy(
                    isAddDataFieldVisible = !isAddDataFieldVisible.value.isAddDataFieldVisible
                )
            }
            is DataFieldEvent.SaveDataFieldName -> {
                if (event.value.length <= maxChar) {
                    _newDataField.value = newDataField.value.copy(
                        fieldName = capitaliseWord(event.value)
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
            is DataFieldEvent.SelectFieldType -> {
                _newDataField.value = newDataField.value.copy(
                    fieldType = event.value
                )
            }
            DataFieldEvent.SaveDataField -> {
                viewModelScope.launch {
                    try {
                        addDataField(
                            dataField =
                            DataField(
                                id = 0,
                                fieldName = _newDataField.value.fieldName,
                                dataValue = "",
                                dataFieldType = _newDataField.value.fieldType,
                                dataList = returnDataList(),
                                isEnabled = true
                            )
                        )

                        _isAddDataFieldVisible.value = _isAddDataFieldVisible.value.copy(
                            isAddDataFieldVisible = !isAddDataFieldVisible.value.isAddDataFieldVisible

                        )
                    } catch (e: InvalidDataFieldException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save DataField"
                            )
                        )
                    }
                }
            }
            is DataFieldEvent.EditDataField -> TODO()
            is DataFieldEvent.EditRowName -> {
                val dataField = dataFieldsBox.get(event.index)
                dataField.fieldName = event.value
                dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.EditRowType -> {
                val dataField = dataFieldsBox.get(event.index)
                dataField.dataFieldType = event.value
                dataFieldsBox.put(dataField)
            }
            is DataFieldEvent.CheckedChange -> {
                val dataField = dataFieldsBox.get(event.index)
                dataField.isEnabled = !dataField.isEnabled
                dataFieldsBox.put(dataField)
            }
        }
    }

    private fun returnDataList(): List<String> {
        //boolean
        when (_newDataField.value.fieldType) {
            3 -> {
                return listOf(
                    _newDataField.value.firstValue,
                    _newDataField.value.secondValue
                )
            }
            //tri-state
            6 -> {
                return listOf(
                    _newDataField.value.firstValue,
                    _newDataField.value.secondValue,
                    _newDataField.value.thirdValue
                )
            }
            else -> {
                return listOf()
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object AddDataField : UiEvent()
    }

}