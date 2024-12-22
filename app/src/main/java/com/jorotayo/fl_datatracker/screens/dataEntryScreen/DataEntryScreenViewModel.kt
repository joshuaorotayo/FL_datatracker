package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.InvalidDataException
import com.jorotayo.fl_datatracker.domain.model.Mappers
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.repository.AppRepository
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import com.jorotayo.fl_datatracker.screens.UiState
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.FormSubmitted
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.SetDataValue
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.SetName
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.UpdateDataId
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.UpdateImageIndex
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent.ValidateInsertDataForm
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataRowState
import com.jorotayo.fl_datatracker.util.getCurrentDateTime
import com.jorotayo.fl_datatracker.util.toString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataEntryScreenViewModel @Inject constructor(
    userPreferenceStore: UserPreferenceStore,
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository,
    private val mapper: Mappers
) : ViewModel() {

    private val settingPreset =
        userPreferenceStore.getString(SettingsKeys.CURRENT_PRESET) ?: "Default"
    private val presetSetting =
        repository.getPresetByPresetName(settingPreset)

    private val dataId = savedStateHandle.get<Int>("dataId") ?: -1

    private val _uiState = MutableStateFlow<UiState<DataEntryScreenState>>(UiState.Empty)
    val uiState: StateFlow<UiState<DataEntryScreenState>> = _uiState.asStateFlow()

    private var currentState = DataEntryScreenState()

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        initData(presetSetting, dataId)
    }

    private fun initData(presetSetting: Preset, dataId: Int) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val longID = dataId.toLong()
            val datafields =
                repository.getDataFieldsByPresetIdEnabled(presetId = presetSetting.presetId)

            if (datafields.isEmpty()) {
                // if preset has no datafields

                _uiState.value = UiState.Empty
            } else if (dataId == -1) {
                // If creating a new record of data to save
                // check for the current preset

                // if preset has some datafields
                val list: MutableList<DataRowState> = ArrayList()
                datafields.forEach { dataField ->
                    list += mapper.mapToDataRowState(
                        dataField = dataField,
                        id = longID,
                        presetId = presetSetting.presetId
                    )
                    /* list += DataRowState(
                         DataItem(
                             dataId = longID,
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
                     )*/
                }

                val newDataFieldScreenState =
                    DataEntryScreenState(dataRows = list, presetSetting = presetSetting)

                delay(5 * 1000L)
                _uiState.value = UiState.Success(newDataFieldScreenState)
                currentState = newDataFieldScreenState
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

                val newDataFieldScreenState = DataEntryScreenState(
                    dataName = currentData.name,
                    dataRows = list,
                    presetSetting = presetSetting
                )

                delay(5 * 1000L)
                _uiState.value = UiState.Success(newDataFieldScreenState)
                currentState = newDataFieldScreenState
            }
        }
    }

    fun onDataEvent(event: DataEvent) {
        when (event) {
            is ValidateInsertDataForm -> onValidateInsertDataForm(event)
            is SetName -> onSetName(event)
            is SetDataValue -> onSetDataValue(event)
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

                    currentState = newUiState.value

                    throw InvalidDataException("Data Form could not be saved. Please check fields")
                } else {
                    if (currentState.currentDataId == (-1).toLong()) {
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
        currentState = currentState.copy(
            dataName = event.value
        )
        _uiState.value = UiState.Success(currentState)
    }

    private fun onSetDataValue(event: SetDataValue) {
        val updatedDataRows = currentState.dataRows.toMutableList().apply {
            this[event.rowIndex].dataItem = this[event.rowIndex].dataItem.copy(
                dataValue = event.value
            )
        }

        currentState = currentState.copy(dataRows = updatedDataRows)

        _uiState.value = UiState.Success(currentState)
    }

    private fun onUpdateDataID(event: UpdateDataId) {
        currentState = currentState.copy(
            currentDataId = event.value
        )
        _uiState.value = UiState.Success(currentState)
    }

    private fun onUpdateImageIndex(event: UpdateImageIndex) {
        currentState = currentState.copy(
            currentImageIndex = event.value
        )
        _uiState.value = UiState.Success(currentState)
    }

    private fun onFormSubmitted() {
        currentState = currentState.copy(
            formSubmitted = true
        )
        _uiState.value = UiState.Success(currentState)
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
        val currentData = repository.getDataByDataId(currentState.currentDataId)

        repository.deleteDataById(currentState.currentDataId)

        val removeDataItems = repository.getDataItemListByDataAndPresetId(
            currentData.dataId,
            currentData.dataPresetId
        )
        for (item in removeDataItems) {
            repository.removeDataItem(item)
        }

        Log.i(TAG, "updateDataForm: ${currentData.dataId}")

        val newData = Data(
            dataId = currentState.currentDataId,
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
        formData: DataEntryScreenState
    ) {
        /*for (item in formData.dataRows) {
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
         }*/
        for (item in formData.dataRows) {
            val newItem = mapper.mapToDataItem(item, dataId)
            repository.addDataItem(newItem)
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveDataForm : UiEvent()
        object UpdateDataForm : UiEvent()
    }
}
