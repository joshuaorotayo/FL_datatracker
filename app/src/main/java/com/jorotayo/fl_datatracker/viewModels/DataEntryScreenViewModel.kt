package com.jorotayo.fl_datatracker.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.InvalidDataException
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.useCases.DataFieldUseCases
import com.jorotayo.fl_datatracker.domain.useCases.DataItemUseCases
import com.jorotayo.fl_datatracker.domain.useCases.DataUseCases
import com.jorotayo.fl_datatracker.domain.useCases.PresetUseCases
import com.jorotayo.fl_datatracker.domain.useCases.SettingsUseCases
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
    savedStateHandle: SavedStateHandle,
    presetUseCases: PresetUseCases,
    settingsUseCases: SettingsUseCases,
) : ViewModel() {

    private val settingPreset = settingsUseCases.getSettingByName(settingName = "currentPreset")
    val presetSetting =
        presetUseCases.getPresetByPresetName(settingPreset.settingStringValue)

    private val dataId = savedStateHandle.get<Long>("dataId") ?: -1

    private var _uiState = mutableStateOf(initData(presetSetting, dataId), neverEqualPolicy())
    val uiState: MutableState<DataEntryScreenState> = _uiState

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onDataEvent(event: DataEvent) {
        when (event) {
            is DataEvent.ValidateInsertDataForm -> onValidateInsertDataForm(event)
            is DataEvent.SetName -> onSetName(event)
            is DataEvent.SetDataValue -> onSetDataValue(event)
            is DataEvent.UpdateUiState -> onUpdateUiState(event)
            is DataEvent.UpdateImageIndex -> onUpdateImageIndex(event)
            is DataEvent.UpdateDataId -> onUpdateDataID(event)
            DataEvent.FormSubmitted -> onFormSubmitted()
        }
    }


    private fun onValidateInsertDataForm(event: DataEvent.ValidateInsertDataForm) {
        viewModelScope.launch {
            try {
                val fieldNames = dataFieldUseCases.getDataFields().map { it.fieldName }
                val dataFormResults =
                    dataUseCases.validateInsertDataForm(
                        fieldNames = fieldNames,
                        dataForm = event.dataEntryScreenState
                    )

                val dataFormValid = dataFormResults.first
                val dataFormData = dataFormResults.second
                if (!dataFormValid) {

                    val newUiState = mutableStateOf(
                        DataEntryScreenState(
                            dataName = dataFormData.dataName,
                            dataRows = dataFormData.dataRows,
                            nameError = dataFormData.nameError,
                            nameErrorMsg = dataFormData.nameErrorMsg,
                            formSubmitted = false
                        )
                    )

                    _uiState.value = newUiState.value

                    throw InvalidDataException("Data Form could not be saved. Please check fields")
                } else {
                    if (uiState.value.currentDataId == (-1).toLong()) {
                        saveDataForm(dataFormResults.second)
                        _eventFlow.emit(UiEvent.SaveDataForm)
                    } else {
                        updateDataForm(dataFormResults.second)
                        _eventFlow.emit(UiEvent.UpdateDataForm)
                    }
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

    private fun onSetName(event: DataEvent.SetName) {
        _uiState.value = uiState.value.copy(
            dataName = event.value
        )
    }

    private fun onSetDataValue(event: DataEvent.SetDataValue) {
        uiState.value.dataRows[event.rowIndex].dataItem =
            _uiState.value.dataRows[event.rowIndex].dataItem.copy(
                dataValue = event.value
            )
    }

    private fun onUpdateUiState(event: DataEvent.UpdateUiState) {
        val newUiState = event.value
        _uiState.value = newUiState
    }

    private fun onUpdateDataID(event: DataEvent.UpdateDataId) {
        _uiState.value = uiState.value.copy(
            currentDataId = event.value
        )
    }

    private fun onUpdateImageIndex(event: DataEvent.UpdateImageIndex) {
        _uiState.value = uiState.value.copy(
            currentImageIndex = event.value
        )
    }

    private fun onFormSubmitted() {
        _uiState.value = uiState.value.copy(
            formSubmitted = true
        )
    }

    private fun saveDataForm(dataForm: DataEntryScreenState) {
        val date = getCurrentDateTime()
        val dateInString = date.toString("HH:mm - dd/MM/yyyy ")

        val newData = Data(
            dataId = 0,
            dataPresetId = presetSetting.presetId,
            name = dataForm.dataName,
            createdTime = dateInString,
            lastEditedTime = dateInString
        )
        saveDataItems(
            dataId = dataUseCases.addData(newData),
            formData = dataForm
        )
    }

    private fun updateDataForm(dataForm: DataEntryScreenState) {
        val currentData = dataUseCases.getDataByDataId(uiState.value.currentDataId)

        val newData = Data(
            dataId = uiState.value.currentDataId,
            name = dataForm.dataName,
            dataPresetId = currentData.dataPresetId,
            createdTime = currentData.createdTime,
            lastEditedTime = getCurrentDateTime().toString("HH:mm - dd/MM/yyyy ")
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
                dataItemId = item.dataItem.dataItemId,
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

    private fun initData(presetSetting: Preset, currentDataId: Long): DataEntryScreenState {
        val list: MutableList<DataRowState> = ArrayList()

        if (currentDataId == (-1).toLong()) {
            // If creating a new record of data to save
            // check for the current preset
            Log.i(TAG, "makeDataRows: NEW " + currentDataId)

            val datafields =
                dataFieldUseCases.getDataFieldsByPresetIdEnabled(presetId = presetSetting.presetId)

            datafields.forEach { dataField ->
                list += DataRowState(
                    DataItem(
                        dataId = currentDataId,
                        presetId = this.presetSetting.presetId,
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
            }

            return DataEntryScreenState(
                dataName = "",
                dataRows = list,
                nameError = false,
                nameErrorMsg = "",
                formSubmitted = false,
                currentImageIndex = 0,
                presetSetting = presetSetting
            )
        } else {
            Log.i(TAG, "current Data Rows value: LOAD  " + uiState.value.currentDataId)

            val currentData = dataUseCases.getDataByDataId(uiState.value.currentDataId)
            val currentDataItems = dataItemUseCases.getDataItemListByDataAndPresetId(
                currentData.dataId,
                presetSetting.presetId
            )

            currentDataItems.forEach { item ->
                list += DataRowState(
                    item
                )
            }

            return DataEntryScreenState(
                dataName = currentData.name,
                dataRows = list,
                nameError = false,
                nameErrorMsg = "",
                formSubmitted = false,
                currentImageIndex = 0,
                presetSetting = presetSetting
            )

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
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveDataForm : UiEvent()
        object UpdateDataForm : UiEvent()
    }
}


