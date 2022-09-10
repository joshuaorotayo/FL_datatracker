package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataRowState
import com.jorotayo.fl_datatracker.util.BoxState
import com.jorotayo.fl_datatracker.util.getCurrentDateTime
import com.jorotayo.fl_datatracker.util.toString
import javax.inject.Inject

class DataEntryScreenViewModel @Inject constructor() : ViewModel() {

    private val _boxState = mutableStateOf(BoxState())
    val boxState: State<BoxState> = _boxState

    private val _currentDataId = mutableStateOf(0.toLong())
    var currentDataId: MutableState<Long> = _currentDataId

    private val _currentDataFields = mutableStateOf(listOf<DataField>())
    var currentDataFields: MutableState<List<DataField>> = _currentDataFields

    private val _dataName = mutableStateOf("")
    var dataName: MutableState<String> = _dataName

    private val _uiState = mutableStateOf(DataEntryScreenState(
        dataName = dataName.value,
        dataRows = makeDataRows(),
        nameError = false
    ))
    var uiState: State<DataEntryScreenState> = _uiState

    fun onEvent(event: DataEvent) {
        when (event) {
            is DataEvent.SaveData -> {

                val date = getCurrentDateTime()
                val dateInString = date.toString("dd/MM/yyyy HH:mm:ss")

                val newData = Data(
                    dataId = 0,
                    // dataFields = returnDataFieldList(event.dataEntryScreenState),
                    name = event.dataEntryScreenState.dataName,
                    lastEdited = dateInString
                )
                boxState.value._dataBox.put(newData)
            }
            is DataEvent.SetName -> {
                _uiState.value = uiState.value.copy(
                    // dataName = event.value
                )
            }
            is DataEvent.SetDataValue -> {
                _uiState.value.dataRows[event.rowIndex].dataItem =
                    uiState.value.dataRows[event.rowIndex].dataItem.copy(
                        dataValue = event.value
                    )
            }
            is DataEvent.UpdateUiState -> {
                _uiState.value = uiState.value.copy(
                    dataRows = event.value.dataRows
                )
            }
        }
    }

    private fun makeDataRows(): MutableList<DataRowState> {
        val list: MutableList<DataRowState> = ArrayList()
        if (currentDataId.value != (0).toLong()) {
            //if loading previously saved data

        } else {
            // If creating a new record of data to save
            // check for the current preset
            val datafields = mutableListOf<DataField>()
            boxState.value.dataFieldsBox.forEach { datafield ->
                if (datafield.presetId == boxState.value.currentPreset?.presetId) {
                    datafields += datafield
                }
            }

            datafields.forEach { dataField ->
                list += DataRowState(
                    DataItem(
                        dataId = currentDataId.value,
                        presetId = boxState.value.currentPreset?.presetId!!,
                        fieldName = dataField.fieldName,
                        dataFieldType = dataField.dataFieldType,
                        first = dataField.first,
                        second = dataField.second,
                        third = dataField.third,
                        isEnabled = dataField.isEnabled,
                        fieldHint = dataField.fieldHint,
                        dataValue = ""
                    )
                )
            }
        }

        return list
    }
}
/*
fun returnDataFieldList(uiState: DataEntryScreenState): List<DataField> {
    val list = ArrayList<DataField>()

    for (dataRow in uiState.dataRows) {
        val newDataField = DataField(
            dataFieldId = dataRow.dataField.dataFieldId,
            fieldName = dataRow.dataField.fieldName,
            dataFieldType = dataRow.dataField.dataFieldType,
            dataValue = dataRow.dataField.dataValue,
            first = dataRow.dataField.first,
            second = dataRow.dataField.second,
            third = dataRow.dataField.third,
            isEnabled = dataRow.dataField.isEnabled,
            fieldHint = dataRow.dataField.fieldHint
        )
        list.add(newDataField)
    }
    return list
}

fun returnDataItemList(uiState: DataEntryScreenState): List<DataItem> {
    val list = ArrayList<DataItem>()
    for (dataRow in uiState.dataRows) {
        val newDataField = DataField(
            dataFieldId = dataRow.dataField.dataFieldId,
            fieldName = dataRow.dataField.fieldName,
            dataFieldType = dataRow.dataField.dataFieldType,
            dataValue = dataRow.dataField.dataValue,
            first = dataRow.dataField.first,
            second = dataRow.dataField.second,
            third = dataRow.dataField.third,
            isEnabled = dataRow.dataField.isEnabled,
            fieldHint = dataRow.dataField.fieldHint
        )
    }
    return list
}*/
