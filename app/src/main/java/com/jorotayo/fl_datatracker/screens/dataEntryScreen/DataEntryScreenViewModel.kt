package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.InvalidDataException
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.repository.AppRepository
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.FormSubmitted
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.SetDataValue
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.SetName
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.UpdateDataId
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.UpdateImageIndex
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.UpdateUiState
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.ValidateInsertDataForm
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataRowState
import com.jorotayo.fl_datatracker.util.getCurrentDateTime
import com.jorotayo.fl_datatracker.util.toString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataEntryScreenViewModel @Inject constructor(
    userPreferenceStore: UserPreferenceStore,
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository
) : ViewModel() {

    private val settingPreset =
        userPreferenceStore.getString(SettingsKeys.CURRENT_PRESET) ?: "Default"
    private val presetSetting =
        repository.getPresetByPresetName(settingPreset)

    private val dataId = savedStateHandle.get<Int>("dataId") ?: -1

    private val _uiState = MutableStateFlow(initData(presetSetting, dataId))
    val uiState = _uiState.asStateFlow()

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onDataEvent(event: DataEvent) {
        when (event) {
            is ValidateInsertDataForm -> onValidateInsertDataForm(event)
            is SetName -> onSetName(event)
            is SetDataValue -> onSetDataValue(event)
            is UpdateUiState -> onUpdateUiState(event)
            is UpdateImageIndex -> onUpdateImageIndex(event)
            is UpdateDataId -> onUpdateDataID(event)
            is FormSubmitted -> onFormSubmitted()
        }
    }

    private fun onValidateInsertDataForm(event: ValidateInsertDataForm) {
        viewModelScope.launch {
            try {
                val fieldNames = repository.getDataFields().map { it.fieldName }
                val dataFormResults =
                    repository.validateInsertDataForm(
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

    private fun onSetName(event: SetName) {
        _uiState.value = uiState.value.copy(
            dataName = event.value
        )
    }

    private fun onSetDataValue(event: SetDataValue) {
        uiState.value.dataRows[event.rowIndex].dataItem =
            _uiState.value.dataRows[event.rowIndex].dataItem.copy(
                dataValue = event.value
            )
    }

    private fun onUpdateUiState(event: UpdateUiState) {
        val newUiState = event.value
        _uiState.value = newUiState
    }

    private fun onUpdateDataID(event: UpdateDataId) {
        _uiState.value = uiState.value.copy(
            currentDataId = event.value
        )
    }

    private fun onUpdateImageIndex(event: UpdateImageIndex) {
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
            dataId = repository.addData(newData),
            formData = dataForm
        )
    }

    private fun updateDataForm(dataForm: DataEntryScreenState) {
        val currentData = repository.getDataByDataId(uiState.value.currentDataId)

        repository.deleteDataById(uiState.value.currentDataId)

        val removeDataItems = repository.getDataItemListByDataAndPresetId(
            currentData.dataId,
            currentData.dataPresetId
        )
        for (item in removeDataItems) {
            repository.removeDataItem(item)
        }

        Log.i(TAG, "updateDataForm: ${currentData.dataId}")

        val newData = Data(
            dataId = uiState.value.currentDataId,
            name = dataForm.dataName,
            dataPresetId = currentData.dataPresetId,
            createdTime = currentData.createdTime,
            lastEditedTime = getCurrentDateTime().toString("HH:mm - dd/MM/yyyy ")
        )
        saveDataItems(
            dataId = repository.addData(newData),
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
            repository.addDataItem(newDataItem)
        }
    }

    private fun initData(presetSetting: Preset, dataId: Int): DataEntryScreenState {
        val longID = dataId.toLong()
        if (this.dataId == -1) {
            // If creating a new record of data to save
            // check for the current preset

            val datafields =
                repository.getDataFieldsByPresetIdEnabled(presetId = presetSetting.presetId)

            val list: MutableList<DataRowState> = ArrayList()
            datafields.forEach { dataField ->
                list += DataRowState(
                    DataItem(
                        dataId = longID,
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
            val currentData = repository.getDataByDataId(longID)
            val currentDataItems = repository.getDataItemsListByDataId(
                currentData.dataId
            )

            Log.i(TAG, "currentData   " + currentDataItems.size)
            val list: MutableList<DataRowState> = ArrayList()
            Log.i(TAG, "original   " + list.size)
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
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveDataForm : UiEvent()
        object UpdateDataForm : UiEvent()
    }
}
