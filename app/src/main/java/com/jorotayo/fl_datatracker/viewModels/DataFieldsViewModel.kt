package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldScreenState
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.NewDataFieldState
import com.jorotayo.fl_datatracker.util.BoxState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataFieldsViewModel @Inject constructor(
) : ViewModel() {

    private val _dataFieldScreenState = mutableStateOf(DataFieldScreenState())
    val dataFieldScreenState: State<DataFieldScreenState> = _dataFieldScreenState

    private val _boxState = mutableStateOf(BoxState())
    var boxState: MutableState<BoxState> = _boxState

    private val currentPresetName = boxState.value.currentPresetSetting?.settingStringValue

    private val _newDataField = mutableStateOf(NewDataFieldState(
        currentPreset = if (currentPresetName.isNullOrEmpty()) "Default" else currentPresetName
    ))
    var newDataField: State<NewDataFieldState> = _newDataField

    private var dataField = DataField(dataFieldId = 0, presetId = 0)

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
                updateDataFields("put", event.value)
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isAddDataFieldVisible = !dataFieldScreenState.value.isAddDataFieldVisible
                )
            }
        }
    }

    private fun updateDataFields(operation: String, dataField: DataField) {
        val newDataFieldBox = ObjectBox.get().boxFor(DataField::class.java)
        if (operation == "put") {
            newDataFieldBox.put(dataField)
        } else {
            newDataFieldBox.remove(dataField)
        }
        _boxState.value = boxState.value.copy(
            dataFieldsList = newDataFieldBox.all
        )

    }

    fun onRowEvent(event: RowEvent) {
        when (event) {
            is RowEvent.EditFieldName -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.fieldName = event.value
            }
            is RowEvent.EditHintText -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.fieldHint = event.value
            }
            is RowEvent.EditRowType -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.dataFieldType = event.value
            }
            is RowEvent.EditIsEnabled -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.isEnabled = !dataField.isEnabled
            }
            is RowEvent.EditFirstValue -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.first = event.value
            }
            is RowEvent.EditSecondValue -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.second = event.value
            }
            is RowEvent.EditThirdValue -> {
                dataField = boxState.value._dataFieldsBox.get(event.index)
                dataField.third = event.value
            }
        }
        boxState.value._dataFieldsBox.put(dataField)
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
                val setting = ObjectBox.get().boxFor(Setting::class.java).get(1)
                setting.settingStringValue = event.value
                updateSettings(setting)
            }
            is PresetEvent.AddPreset -> {
                val newPresetBox = ObjectBox.get().boxFor(Preset::class.java)
                newPresetBox.put(
                    Preset(
                        presetId = 0,
                        presetName = event.value
                    )
                )
                _boxState.value = boxState.value.copy(
                    presetsBox = newPresetBox.all
                )
                PresetEvent.ChangePreset(event.value)
            }
            is PresetEvent.DeletePreset -> {
                deletePresetActions(event.value)
            }
        }
    }

    private fun updateSettings(setting: Setting) {
        val newSettingBox = ObjectBox.get().boxFor(Setting::class.java)
        newSettingBox.put(setting)
        _boxState.value = boxState.value.copy(
            settingsBox = newSettingBox.all
        )
    }

    private fun deletePresetActions(preset: Preset) {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            isPresetDeleteDialogVisible = if (!dataFieldScreenState.value.isPresetDeleteDialogVisible.value) mutableStateOf(
                true) else mutableStateOf(false)
        )

        val newDataFieldBox = mutableListOf<DataField>()

        val newPresetBox = ObjectBox.get().boxFor(Preset::class.java)
        newPresetBox.remove(preset)
        for (data in boxState.value.dataFieldsList) {
            if (data.presetId != preset.presetId) {
                newDataFieldBox += data
            }
        }

//        _boxState.value.dataFieldsList = newDataFieldBox

        _boxState.value = boxState.value.copy(
            dataFieldsList = newDataFieldBox,
            presetsBox = newPresetBox.all
        )

        PresetEvent.ChangePreset("Default")

    }
}