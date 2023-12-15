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
import com.jorotayo.fl_datatracker.domain.useCases.DataFieldUseCases
import com.jorotayo.fl_datatracker.domain.useCases.PresetUseCases
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys.CURRENT_PRESET
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
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
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ShowAddPresetDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ShowDeletePresetDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditFieldName
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditFirstValue
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditHintText
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditIsEnabled
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditRowType
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditSecondValue
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.EditThirdValue
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent.ToggleRow
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldScreenState
import com.jorotayo.fl_datatracker.util.components.AlertDialogState
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel.UiEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataFieldsViewModel @Inject constructor(
    private val dataFieldUseCases: DataFieldUseCases,
    private val presetUseCases: PresetUseCases,
    private val userPreferenceStore: UserPreferenceStore
) : ViewModel() {
    private val currentPresetName: MutableState<String> =
        mutableStateOf(userPreferenceStore.getString(CURRENT_PRESET) ?: "Default")
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
            ExpandPresetDropdown -> onExpandPresetDropdown()
            HidePresetDropdown -> onHidePresetDropdown()
            ToggleAddNewDataField -> onToggleAddNewDataField()
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

            userPreferenceStore.setString(Pair(CURRENT_PRESET, "Default"))
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
                _eventFlow.emit(SaveDataField("Data Field Saved: ${event.value.fieldName}"))
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    isAddDataFieldVisible = !dataFieldScreenState.value.isAddDataFieldVisible
                )
                updateDataFields()
            } catch (e: InvalidDataFieldException) {
                _eventFlow.emit(
                    ShowSnackbar(
                        message = e.message ?: "DataField Not saved"
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
            is EditFieldName -> onEditFieldName(event)
            is EditHintText -> onEditHintText(event)
            is EditRowType -> onEditRowType(event)
            is EditIsEnabled -> onEditIsEnabled(event)
            is EditFirstValue -> onEditFirstValue(event)
            is EditSecondValue -> onEditSecondValue(event)
            is EditThirdValue -> onEditThirdValue(event)
            is ToggleRow -> onRowToggle(event)
        }
        ObjectBox.boxStore().boxFor(DataField::class.java).put(dataField)

        dataFieldUseCases.updateDataField(dataField)
        _dataFieldScreenState.value = dataFieldScreenState.value.copy(
            dataFields = dataFieldUseCases.getDataFieldsByPresetId(currentPreset.presetId)
        )
    }

    private fun onRowToggle(event: ToggleRow) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.isEnabled = !dataField.isEnabled
    }

    private fun onEditFieldName(event: EditFieldName) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.fieldName = event.value
    }

    private fun onEditHintText(event: EditHintText) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.fieldHint = event.value
    }

    private fun onEditRowType(event: EditRowType) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.dataFieldType = event.value
    }

    private fun onEditIsEnabled(event: EditIsEnabled) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.isEnabled = !dataField.isEnabled
    }

    private fun onEditFirstValue(event: EditFirstValue) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.first = event.value
    }

    private fun onEditSecondValue(event: EditSecondValue) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.second = event.value
    }

    private fun onEditThirdValue(event: EditThirdValue) {
        dataField =
            dataFieldScreenState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.third = event.value
    }

    fun onPresetEvent(event: PresetEvent) {
        when (event) {
            is ShowAddPresetDialog -> onShowAddPresetDialog()
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
            newPreset = null,
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
            if (newPreset == null || newPreset.presetName.isBlank()) {
                _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                    alertDialogState = _dataFieldScreenState.value.alertDialogState?.copy(
                        textFieldErrorText = "Enter Preset Name!"
                    )
                )
            } else {
                try {
                    presetUseCases.addPreset(newPreset)
                    val presets = presetUseCases.getPresetList()
                    val currentPreset = presetUseCases.getPresetByPresetName(newPreset.presetName)
                    _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                        alertDialogState = null,
                        isPresetDropDownMenuExpanded = false,
                        isAddDataFieldVisible = false,
                        presetList = presets,
                        currentPreset = newPreset,
                        dataFields = dataFieldUseCases.getDataFieldsByPresetId(currentPreset.presetId),
                    )
                    userPreferenceStore.setString(Pair(CURRENT_PRESET, newPreset.presetName))
                    _eventFlow.emit(ShowSnackbar("Preset: ${newPreset.presetName} added!"))
                } catch (e: InvalidPresetException) {
                    _dataFieldScreenState.value = dataFieldScreenState.value.copy(
                        alertDialogState = null,
                        isPresetDropDownMenuExpanded = false,
                        isAddDataFieldVisible = false
                    )
                    _eventFlow.emit(
                        ShowSnackbar(message = e.message!!)
                    )
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

        userPreferenceStore.setString(Pair(CURRENT_PRESET, event.value))

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
        data class SaveDataField(val message: String) : UiEvent()
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}
