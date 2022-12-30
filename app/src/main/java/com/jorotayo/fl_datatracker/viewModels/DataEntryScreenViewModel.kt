package com.jorotayo.fl_datatracker.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.InvalidDataException
import com.jorotayo.fl_datatracker.domain.useCases.*
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataRowState
import com.jorotayo.fl_datatracker.util.getCurrentDateTime
import com.jorotayo.fl_datatracker.util.toString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataEntryScreenViewModel @Inject constructor(
    private val dataFieldUseCases: DataFieldUseCases,
    private val dataItemUseCases: DataItemUseCases,
    private val dataUseCases: DataUseCases,
    presetUseCases: PresetUseCases,
    settingsUseCases: SettingsUseCases,
) : ViewModel() {

    private val settingPreset = settingsUseCases.getSettingByName(settingName = "currentPreset")
    val presetSetting =
        presetUseCases.getPresetByPresetName(settingPreset.settingStringValue)

    private val _currentDataId = mutableStateOf(0.toLong())
    var currentDataId: MutableState<Long> = _currentDataId

    private val _currentDataFields = mutableStateOf(listOf<DataField>())
    var currentDataFields: MutableState<List<DataField>> = _currentDataFields

    private val _dataName = mutableStateOf("")
    var dataName: MutableState<String> = _dataName

    private var _uiState = mutableStateOf(DataEntryScreenState(
        dataName = dataName.value,
        dataRows = makeDataRows(),
        nameError = false,
        nameErrorMsg = ""
    ))
    val uiState: State<DataEntryScreenState> = _uiState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: DataEvent) {
        when (event) {
            is DataEvent.ValidateDataForm -> {
                viewModelScope.launch {
                    try {
                        val fieldNames = dataFieldUseCases.getDataFields().map { it.fieldName }
                        val dataFormResults =
                            dataUseCases.validateInsertDataForm(fieldNames = fieldNames,
                                dataForm = event.dataEntryScreenState)
                        if (!dataFormResults.first) {

                            val newUiState = mutableStateOf(DataEntryScreenState(
                                dataName = dataFormResults.second.dataName,
                                dataRows = dataFormResults.second.dataRows,
                                nameError = dataFormResults.second.nameError,
                                nameErrorMsg = dataFormResults.second.nameErrorMsg
                            ))
                            _uiState.value = newUiState.value
                            throw InvalidDataException("Data Form could not be saved. Please check fields")
                        } else {
                            saveDataForm(dataFormResults.second)
                            _eventFlow.emit(UiEvent.SaveDataForm)
                        }
                    } catch (e: InvalidDataException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message.toString().ifBlank { "" }
                            )
                        )
                    }
                }
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

    private fun saveDataForm(dataForm: DataEntryScreenState) {
        val date = getCurrentDateTime()
        val dateInString = date.toString("HH:mm - dd/MM/yyyy ")

        val newData = Data(
            dataId = 0,
            name = dataForm.dataName,
            createdTime = dateInString,
            lastEditedTime = dateInString
        )
        saveDataItems(
            dataId = dataUseCases.addData(newData),
            formData = dataForm
        )
    }

    private fun saveDataItems(
        dataId: Long,
        formData: DataEntryScreenState,
    ) {
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
                fieldDescription = item.dataItem.fieldDescription,
                dataValue = item.dataItem.dataValue,
            )
            dataItemUseCases.addDataItem(newDataItem)
        }
    }

    private fun makeDataRows(): MutableList<DataRowState> {
        val list: MutableList<DataRowState> = ArrayList()

        Log.i(TAG, "current Data Rows value: LOAD  " + currentDataFields)
        if (currentDataId.value != (0).toLong()) {
            //Returns all enabled data fields in the data item
            // val dataFields = dataBox.value.get(currentDataId.value).dataFields.filter { it.isEnabled }

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

            val datafields =
                dataFieldUseCases.getDataFieldsByPresetIdEnabled(presetId = presetSetting.presetId)

            datafields.forEach { dataField ->
                list += DataRowState(

                    DataItem(
                        dataId = currentDataId.value,
//                        presetId = boxState.value.currentPreset?.presetId!!,
                        presetId = presetSetting.presetId,
                        fieldName = dataField.fieldName,
                        dataFieldType = dataField.dataFieldType,
                        first = dataField.first,
                        second = dataField.second,
                        third = dataField.third,
                        isEnabled = dataField.isEnabled,
                        fieldDescription = dataField.fieldHint,
                        dataValue = ""
                    )
                )
                Log.d("datafield type", "makeDataRows: " + dataField.dataFieldType.toString())
            }
        }
        return list
    }


    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveDataForm : UiEvent()
    }

}


