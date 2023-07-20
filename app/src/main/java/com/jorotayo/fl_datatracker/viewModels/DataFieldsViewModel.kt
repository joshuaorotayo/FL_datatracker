package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.InvalidDataFieldException
import com.jorotayo.fl_datatracker.domain.model.InvalidPresetException
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.useCases.DataFieldUseCases
import com.jorotayo.fl_datatracker.domain.useCases.PresetUseCases
import com.jorotayo.fl_datatracker.domain.useCases.SettingsUseCases
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.DeleteDataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.ExpandPresetDropdown
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.HidePresetDropdown
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.RestoreDeletedField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.SaveDataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.ShowDeleteRowDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.ToggleAddNewDataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ChangePreset
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.DeletePreset
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.EditPresetName
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ShowDeletePresetDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldScreenState
import com.jorotayo.fl_datatracker.util.components.AlertDialogState
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
    val currentPresetName: MutableState<String> =
        mutableStateOf(settingsUseCases.getSettingsList().last().settingStringValue)
    private var currentPreset = presetUseCases.getPresetByPresetName(currentPresetName.value)

    private var _dataFieldScreenState = mutableStateOf(
        DataFieldScreenState(
            presetList = presetUseCases.getPresetList(),
            currentPreset = currentPreset,
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(currentPreset.presetId),
        )
    )
    val dataFieldScreenState: State<DataFieldScreenState> = _dataFieldScreenState


    private var dataField = DataField(dataFieldId = 0, presetId = 0)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onDataFieldEvent(event: DataFieldEvent) {
        when (event) {
            DataFieldEvent.InitScreen -> onInitScreen()
            RestoreDeletedField -> onRestoreDataField()
            ToggleAddNewDataField -> onToggleAddNewDataField()
            ExpandPresetDropdown -> onExpandPresetDropdown()
            HidePresetDropdown -> onHidePresetDropdown()
            is DeleteDataField -> onDeleteDataField(event)
            is ShowDeleteRowDialog -> onShowDeleteRowDialog(event)
            is SaveDataField -> onSaveDataField(event)
        }
    }

    private fun onInitScreen() {
        val presetList = presetUseCases.getPresetList()
        if (presetList.isEmpty()) {
            presetUseCases.addPreset(
                Preset(
                    presetId = 0,
                    presetName = "Default"
                )
            )

            settingsUseCases.addSetting(
                Setting(
                    settingId = 0,
                    settingName = "currentPreset",
                    settingBoolValue = false,
                    settingStringValue = "Default"
                )
            )
        }
    }

    private fun onDeleteDataField(event: DeleteDataField) {
        dataFieldUseCases.deleteDataField(event.value)
        updateDataFields()
    }

    private fun onRestoreDataField() {
        if (dataFieldScreenState.value.deletedDataField != null) {
            dataFieldUseCases.addDataField(dataFieldScreenState.value.deletedDataField!!)
            updateDataFields()
        }
    }

    private fun onSaveDataField(event: SaveDataField) {
        viewModelScope.launch {
            try {
                dataFieldUseCases.addDataField(event.value)
                _eventFlow.emit(UiEvent.SaveDataField)
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isAddDataFieldVisible = !dataFieldScreenState.value.isAddDataFieldVisible
                )
                updateDataFields()
            } catch (e: InvalidDataFieldException) {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        message = "Data Field Not Saved!"
                    )
                )
            }
        }
    }

    private fun onShowDeleteRowDialog(event: ShowDeleteRowDialog) {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            currentDataField = event.value,
            alertDialogState = AlertDialogState(
                title = String.format("Delete DataField: %s", event.value.fieldName),
                imageIcon = Icons.Default.Delete,
                text = "Are you sure you want to delete this Data Field?",
                onDismissRequest = { onDismissAlertDialog() },
                confirmButtonLabel = "Delete",
                confirmButtonOnClick = {
                    dataFieldUseCases.deleteDataField(_dataFieldScreenState.value.currentDataField!!)
                    updateDataFields()
                    onDismissAlertDialog()
                },
                dismissButtonLabel = "Cancel",
                dismissButtonOnClick = { onDismissAlertDialog() },
                titleTextAlign = TextAlign.Center,
                dismissible = true
            )
        )
    }

    private fun onExpandPresetDropdown() {
        _dataFieldScreenState.value = _dataFieldScreenState.value.copy(
            isPresetDropDownMenuExpanded = true
        )
    }

    private fun onHidePresetDropdown() {
        _dataFieldScreenState.value = _dataFieldScreenState.value.copy(
            isPresetDropDownMenuExpanded = false
        )
    }

    private fun onToggleAddNewDataField() {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            isAddDataFieldVisible = !dataFieldScreenState.value.isAddDataFieldVisible
        )
    }

    private fun updateDataFields() {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(currentPreset.presetId)
        )
    }

    private fun updatePresetList() {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            presetList = presetUseCases.getPresetList()
        )
    }

    fun onRowEvent(event: RowEvent) {
        when (event) {
            is RowEvent.EditFieldName -> onEditFieldName(event)
            is RowEvent.EditHintText -> onEditHintText(event)
            is RowEvent.EditRowType -> onEditRowType(event)
            is RowEvent.EditIsEnabled -> onEditIsEnabled(event)
            is RowEvent.EditFirstValue -> onEditFirstValue(event)
            is RowEvent.EditSecondValue -> onEditSecondValue(event)
            is RowEvent.EditThirdValue -> onEditThirdValue(event)
            is RowEvent.ToggleRow -> onRowToggle(event)
        }
        ObjectBox.get().boxFor(DataField::class.java).put(dataField)

        dataFieldUseCases.updateDataField(dataField)
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(currentPreset.presetId)
        )
    }

    private fun onRowToggle(event: RowEvent.ToggleRow) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.isEnabled = !dataField.isEnabled
    }

    private fun onEditFieldName(event: RowEvent.EditFieldName) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.fieldName = event.value
    }

    private fun onEditHintText(event: RowEvent.EditHintText) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.fieldHint = event.value
    }

    private fun onEditRowType(event: RowEvent.EditRowType) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.dataFieldType = event.value
    }

    private fun onEditIsEnabled(event: RowEvent.EditIsEnabled) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.isEnabled = !dataField.isEnabled
    }

    private fun onEditFirstValue(event: RowEvent.EditFirstValue) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.first = event.value
    }

    private fun onEditSecondValue(event: RowEvent.EditSecondValue) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.second = event.value
    }

    private fun onEditThirdValue(event: RowEvent.EditThirdValue) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.third = event.value
    }

    fun onPresetEvent(event: PresetEvent) {
        when (event) {
            is PresetEvent.ShowAddPresetDialog -> onShowAddPresetDialog()
            is ShowDeletePresetDialog -> onShowDeletePresetDialog(event)
            is ChangePreset -> onChangePreset(event)
            is EditPresetName -> onEditPresetName(event)
            is DeletePreset -> onDeletePreset()
        }
    }

    private fun onDismissAlertDialog() {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            alertDialogState = null
        )
    }

    private fun onEditPresetName(event: EditPresetName) {
        val newPreset = Preset(0, event.value)
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            newPreset = newPreset
        )
    }

    private fun onShowAddPresetDialog() {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            isPresetDropDownMenuExpanded = false,
            alertDialogState = AlertDialogState(
                title = "Add New Preset",
                imageIcon = Icons.Default.AddBox,
                text = "Add a new Preset with the name?",
                onDismissRequest = { onDismissAlertDialog() },
                confirmButtonLabel = "Add Preset",
                confirmButtonOnClick = { saveNewPreset() },
                dismissButtonLabel = "Cancel",
                editFieldFunction = {
                    onPresetEvent(EditPresetName(it))
                },
                dismissButtonOnClick = { onDismissAlertDialog() },
                titleTextAlign = TextAlign.Center,
                dismissible = true
            )
        )
    }

    private fun saveNewPreset() {
        val newPreset = _dataFieldScreenState.value.newPreset

        viewModelScope.launch {
            try {
                presetUseCases.addPreset(newPreset!!)
                val presets = presetUseCases.getPresetList()
                val currentPreset = presetUseCases.getPresetByPresetName(newPreset.presetName)
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    alertDialogState = null,
                    isPresetDropDownMenuExpanded = false,
                    isAddDataFieldVisible = false,
                    presetList = presets,
                    dataFields = dataFieldUseCases.getDataFieldsByPresetId(currentPreset.presetId),
                )
                _eventFlow.emit(UiEvent.ShowSnackbar("Preset: ${newPreset.presetName} added!"))

            } catch (e: InvalidPresetException) {
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    alertDialogState = null,
                    isPresetDropDownMenuExpanded = false,
                    isAddDataFieldVisible = false
                )
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(message = e.message!!)
                )
            } finally {
                if (newPreset != null) {
                    ChangePreset(newPreset.presetName)
                }
            }
        }
    }

    private fun onShowDeletePresetDialog(event: ShowDeletePresetDialog) {
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            modifiedPreset = event.value,
            alertDialogState = AlertDialogState(
                title = String.format("Delete Preset: %s", event.value.presetName),
                text = "Are you sure you want to delete this Preset?",
                onDismissRequest = { onDismissAlertDialog() },
                confirmButtonLabel = "Delete",
                confirmButtonOnClick = {
                    onDeletePreset()
                },
                dismissButtonLabel = "Cancel",
                dismissButtonOnClick = { onDismissAlertDialog() },
                titleTextAlign = TextAlign.Center,
                dismissible = true
            )
        )
    }

    private fun onChangePreset(event: ChangePreset) {
        val newPreset = presetUseCases.getPresetByPresetName(event.value)

        val newSettingPreset = settingsUseCases.getSettingByName("currentPreset")
        settingsUseCases.editSetting(
            newSettingPreset.copy(settingStringValue = event.value)
        )

        currentPreset = newPreset
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(newPreset.presetId),
            isPresetDropDownMenuExpanded = false,
            isAddDataFieldVisible = false,
            presetList = presetUseCases.getPresetList(),
            currentPreset = newPreset,
            alertDialogState = null
        )
    }


    private fun onDeletePreset() {
        _dataFieldScreenState.value.modifiedPreset?.let { deletePresetActions(it) }
    }

    private fun deletePresetActions(preset: Preset) {
        val removeDataFields = dataFieldUseCases.getDataFieldsByPresetId(preset.presetId)

        dataFieldUseCases.deleteDataFields(removeDataFields)

        presetUseCases.deletePreset(preset)

        val newPreset = presetUseCases.getPresetByPresetName("Default")
        currentPreset = newPreset
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(newPreset.presetId),
            presetList = presetUseCases.getPresetList(),
            currentPreset = newPreset,
            isAddDataFieldVisible = false,
            isPresetDropDownMenuExpanded = false,
            alertDialogState = null
        )
    }

    sealed class UiEvent {
        object SaveDataField : UiEvent()
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}
