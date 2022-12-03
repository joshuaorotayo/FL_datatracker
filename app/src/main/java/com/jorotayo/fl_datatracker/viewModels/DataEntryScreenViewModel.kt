package com.jorotayo.fl_datatracker.viewModels

import android.content.ContentValues.TAG
import android.util.Log
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

    /*
        init{
            savedStateHandle.get<Long>("dataId")?.let {
                _currentDataId.value = it
            }
        }*/
    private var _uiState = mutableStateOf(DataEntryScreenState(
        dataName = dataName.value,
        dataRows = makeDataRows(),
        nameError = false
    ))

    val uiState: State<DataEntryScreenState> = _uiState


    fun onEvent(event: DataEvent) {
        when (event) {
            is DataEvent.SaveData -> {

                val date = getCurrentDateTime()
                val dateInString = date.toString("HH:mm - dd/MM/yyyy ")

                val newData = Data(
                    dataId = 0,
                    name = event.dataEntryScreenState.dataName,
                    createdTime = dateInString,
                    lastEditedTime = dateInString
                )
                saveDataItems(
                    dataId = boxState.value._dataBox.put(newData),
                    formData = event.dataEntryScreenState)

            }
            is DataEvent.SetName -> {
                _uiState.value = uiState.value.copy(
                    dataName = event.value
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
            is DataEvent.UpdateDataId -> {
                _currentDataId.value = event.value
            }
        }
    }

    private fun makeDataRows(): MutableList<DataRowState> {
        val list: MutableList<DataRowState> = ArrayList()

        if (currentDataId.value != (0).toLong()) {
            //Returns all enabled data fields in the data item
            // val dataFields = dataBox.value.get(currentDataId.value).dataFields.filter { it.isEnabled }

            Log.i(TAG, "makeDataRows: LOAD  " + currentDataId.value)
            // All stored fields should be enabled
            /* currentDataFields.value = dataBox.value.get(currentDataId.value).dataFields.toList()

             //uses the Data fields values to populate the Data Rows
             for (df in currentDataFields.value) {
                 list.add(DataRowState(
                     dataField = df,
                     hasError = false,
                     errorMsg = ""
                 ))
             }*/
        } else {
            // If creating a new record of data to save
            // check for the current preset
            Log.i(TAG, "makeDataRows: NEW " + currentDataId.value)
            val datafields = mutableListOf<DataField>()
            boxState.value.dataFieldsList.forEach { datafield ->
                if ((datafield.presetId == boxState.value.currentPreset?.presetId)) {
                    if (datafield.isEnabled) {
                        datafields += datafield
                    }
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

private fun saveDataItems(dataId: Long, formData: DataEntryScreenState) {
    for (item in formData.dataRows) {
        val newDataItem = DataItem(
            dataId = dataId,
            dataItemId = 0,
            presetId = item.dataItem.presetId,
            fieldName = item.dataItem.fieldName,
            dataFieldType = item.dataItem.dataFieldType,
            first = item.dataItem.first,
            second = item.dataItem.second,
            third = item.dataItem.third,
            isEnabled = item.dataItem.isEnabled,
            fieldHint = item.dataItem.fieldHint,
            dataValue = item.dataItem.dataValue,
        )
        BoxState()._dataItemBox.put(newDataItem)
    }
}
