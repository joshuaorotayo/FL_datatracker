package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.InvalidDataFieldException
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.useCases.DataFieldUseCases
import com.jorotayo.fl_datatracker.domain.useCases.PresetUseCases
import com.jorotayo.fl_datatracker.domain.useCases.SettingsUseCases
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldScreenState
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.NewDataFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataFieldsViewModel @Inject constructor(
    private val dataFieldUseCases: DataFieldUseCases,
    private val presetUseCases: PresetUseCases,
    private val settingsUseCases: SettingsUseCases,
) : ViewModel() {

    private val settingPreset = settingsUseCases.getSettingByName(settingName = "currentPreset")
    private val presetSetting =
        presetUseCases.getPresetByPresetName(settingPreset.settingStringValue)
    private val newPresetList = presetUseCases.getPresetList()

    private val _currentPreset: MutableState<Preset> = mutableStateOf(presetSetting)
    val currentPreset: State<Preset> = _currentPreset

    private val _dataFieldScreenState = mutableStateOf(DataFieldScreenState(
        dataFields = dataFieldUseCases.getDataFieldsByPresetId(presetSetting.presetId),
        presetList = newPresetList,
        currentPreset = currentPreset.value
    ))
    val dataFieldScreenState: State<DataFieldScreenState> = _dataFieldScreenState


    private val _newDataField =
        mutableStateOf(NewDataFieldState(currentPreset = currentPreset.value.presetName))
    var newDataField: MutableState<NewDataFieldState> = _newDataField

    private var dataField = DataField(dataFieldId = 0, presetId = 0)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onDataEvent(event: DataFieldEvent) {
        when (event) {
            is DataFieldEvent.ToggleAddNewDataField -> {
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isAddDataFieldVisible = !dataFieldScreenState.value.isAddDataFieldVisible
                )
            }
            is DataFieldEvent.AddFieldName -> {
                if (event.value.length <= dataFieldScreenState.value.maxChar) {
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
                if (event.value.length <= dataFieldScreenState.value.maxHintChar) {
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
            is DataFieldEvent.DeleteDataField -> {
                updateDataFields("remove", event.value)
            }
            is DataFieldEvent.RestoreDeletedField -> {
                updateDataFields("put", dataFieldScreenState.value.deletedDataField)
            }
            is DataFieldEvent.OpenDeleteDialog -> {
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isDeleteDialogVisible = if (!dataFieldScreenState.value.isDeleteDialogVisible.value) mutableStateOf(
                        true) else mutableStateOf(false)
                )
                dataFieldScreenState.value.deletedDataField = event.dataField
            }
            is DataFieldEvent.SaveDataField -> {
                viewModelScope.launch {
                    try {
                        dataFieldUseCases.addDataField(event.value)
                        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                            dataFields = dataFieldUseCases.getDataFieldsByPresetId(currentPreset.value.presetId)
                        )
                        _eventFlow.emit(UiEvent.SaveDataField)
                    } catch (e: InvalidDataFieldException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = "Data Field Not Saved!"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun updateDataFields(operation: String, dataField: DataField) {
        if (operation == "put") {
            dataFieldUseCases.addDataField(dataField)
        } else {
            dataFieldUseCases.deleteDataField(dataField)
        }

        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(currentPreset.value.presetId)
        )

    }

    fun onRowEvent(event: RowEvent) {
        when (event) {
            is RowEvent.EditFieldName -> {
                dataField =
                    dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
                dataField.fieldName = event.value
            }
            is RowEvent.EditHintText -> {
                dataField =
                    dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
                dataField.fieldHint = event.value
            }
            is RowEvent.EditRowType -> {
                dataField =
                    dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
                dataField.dataFieldType = event.value
            }
            is RowEvent.EditIsEnabled -> {
                dataField =
                    dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
                dataField.isEnabled = !dataField.isEnabled
            }
            is RowEvent.EditFirstValue -> {
                dataField =
                    dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
                dataField.first = event.value
            }
            is RowEvent.EditSecondValue -> {
                dataField =
                    dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
                dataField.second = event.value
            }
            is RowEvent.EditThirdValue -> {
                dataField =
                    dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
                dataField.third = event.value
            }
        }
        ObjectBox.get().boxFor(DataField::class.java).put(dataField)

        dataFieldUseCases.updateDataField(dataField)
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(currentPreset.value.presetId)
        )
    }

    fun onPresetEvent(event: PresetEvent) {
        when (event) {
            is PresetEvent.TogglePresetDialog -> {
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isAddPresetDialogVisible = if (!dataFieldScreenState.value.isAddPresetDialogVisible.value) mutableStateOf(
                        true) else mutableStateOf(false)
                )
            }
            is PresetEvent.TogglePresetDeleteDialog -> {
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    deletedPreset = event.value,
                    isPresetDeleteDialogVisible = if (!dataFieldScreenState.value.isPresetDeleteDialogVisible.value) mutableStateOf(
                        true) else mutableStateOf(false)
                )
            }
            is PresetEvent.ChangePreset -> {
                settingsUseCases.addSetting(
                    Setting(
                        settingId = 1,
                        settingName = "currentPreset",
                        settingBoolValue = false,
                        settingStringValue = event.value
                    )
                )

                val newCurrentPreset = presetUseCases.getPresetByPresetName(event.value)

                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    currentPreset = presetUseCases.getPresetByPresetName(event.value),
                    dataFields = dataFieldUseCases.getDataFieldsByPresetId(newCurrentPreset.presetId),
                    isAddDataFieldVisible = false
                )
                updatePresets()
            }
            is PresetEvent.AddPreset -> {
                PresetEvent.ChangePreset(event.value)
                val newPresetBox = ObjectBox.get().boxFor(Preset::class.java)
                newPresetBox.put(
                    Preset(
                        presetId = 0,
                        presetName = event.value
                    )
                )
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    presetList = newPresetBox.all
                )
                // PresetEvent.ChangePreset(newPresetBox.all.last().presetName)
            }
            is PresetEvent.DeletePreset -> {
                deletePresetActions(event.value)
            }
        }
    }

    private fun updatePresets() {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            presetList = presetUseCases.getPresetList()
        )
    }

    private fun deletePresetActions(preset: Preset) {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            isPresetDeleteDialogVisible = if (!dataFieldScreenState.value.isPresetDeleteDialogVisible.value) mutableStateOf(
                true) else mutableStateOf(false)
        )
        val newPresetBox = ObjectBox.get().boxFor(Preset::class.java)
        newPresetBox.remove(preset)

/*        _boxState.value = boxState.value.copy(
            dataFieldsList = newDataFieldBox,
            presetsBox = newPresetBox.all
        )

        PresetEvent.ChangePreset("Default")


        for (data in boxState.value.dataFieldsList) {
            if (data.presetId != preset.presetId) {
                newDataFieldBox += data
            }
        }*/
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveDataField : UiEvent()
    }

    /*private fun getDataFields() {
        getDataFieldsJob?.cancel()
        Log.i(TAG, "getDataFields: refresh")


        val viewPreset =
            currentPreset.let { presetUseCases.getPresetByPresetName(it.value.presetName) }

        getDataFieldsJob = if (viewPreset != null) {
            dataFieldUseCases.getDataFieldsByPresetId(viewPreset.presetId).conflate()
                .launchIn(viewModelScope)
        } else {
            dataFieldUseCases.getDataFieldsByPresetId(presetId = 1).conflate()
                .launchIn(viewModelScope)
        }
    }*/
}