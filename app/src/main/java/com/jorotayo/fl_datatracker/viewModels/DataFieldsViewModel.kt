package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.InvalidDataFieldException
import com.jorotayo.fl_datatracker.domain.util.use_case.DataFieldUseCases
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.DataFieldScreenState
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.NewDataFieldState
import com.jorotayo.fl_datatracker.util.capitaliseWord
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataFieldsViewModel @Inject constructor(
) : ViewModel() {

    private val _dataFieldsBox2 = mutableStateOf(ObjectBox.get().boxFor(DataField::class.java))
    var dataFieldsBox2: State<Box<DataField>> = _dataFieldsBox2

    init {
        loadDataFields()
    }

    private fun loadDataFields() {
        _dataFieldsBox2.value = ObjectBox.get().boxFor(DataField::class.java)
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
            is DataFieldEvent.EditDataField -> TODO()
            is DataFieldEvent.EditRowName -> {
                val dataField = dataFieldsBox2.value.get(event.index)
                dataField.fieldName = event.value
                dataFieldsBox2.value.put(dataField)
            }
            is DataFieldEvent.EditRowType -> {
                val dataField = dataFieldsBox2.value.get(event.index)
                dataField.dataFieldType = event.value
                dataFieldsBox2.value.put(dataField)
            }
            is DataFieldEvent.CheckedChange -> {
                val dataField = dataFieldsBox2.value.get(event.index)
                dataField.isEnabled = !dataField.isEnabled
                dataFieldsBox2.value.put(dataField)
            }
            is DataFieldEvent.SaveDataField -> {
                viewModelScope.launch {
                    try {
                        DataFieldUseCases().addDataField(
                            dataField = event.dataField
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
            is DataFieldEvent.EditStateValues -> {
                val dataField = dataFieldsBox2.value.get(event.index)
                val dataList = dataField.dataList?.toMutableList()
                dataList?.set(event.valIndex, event.value)
                if (dataList != null) {
                    dataField.dataList = dataList.toList()
                }
                dataFieldsBox2.value.put(dataField)
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveDataField : UiEvent()
    }

}