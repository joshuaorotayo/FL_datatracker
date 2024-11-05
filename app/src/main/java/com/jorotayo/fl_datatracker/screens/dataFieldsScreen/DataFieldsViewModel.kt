package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.InvalidDataFieldException
import com.jorotayo.fl_datatracker.domain.model.InvalidPresetException
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.repository.DataFieldRepository
import com.jorotayo.fl_datatracker.domain.repository.PresetRepository
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys.CURRENT_PRESET
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldsViewModel.UiEvent.*
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataFieldsViewModel @Inject constructor(
    private val dataFieldRepo: DataFieldRepository,
    private val presetRepo: PresetRepository,
    private val userPreferenceStore: UserPreferenceStore
) : ViewModel() {

    private val currentPresetName: MutableState<String> =
        mutableStateOf(userPreferenceStore.getString(CURRENT_PRESET) ?: "Default")
    private var currentPreset = presetRepo.getPresetByPresetName(currentPresetName.value)

    private var _uiState = mutableStateOf(
        DataFieldScreenState(
            presetList = presetRepo.getPresetList(),
            currentPreset = currentPreset,
            dataFields = dataFieldRepo.getDataFieldsByPresetId(currentPreset.presetId),
        )
    )
    val uiState: MutableState<DataFieldScreenState> = _uiState

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

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
            DataFieldEvent.ToggleMemberForm -> onToggleMemberForm()
            is DeleteDataField -> onDeleteDataField(event)
            is ShowDeleteRowDialog -> onShowDeleteRowDialog(event)
            is SaveDataField -> onSaveDataField(event)
        }
    }

    private fun onInitScreen() {
        val presetList = presetRepo.getPresetList()
        if (presetList.isEmpty()) {
            presetRepo.addPreset(
                Preset(
                    presetId = 0,
                    presetName = "Default"
                )
            )

            userPreferenceStore.setString(Pair(CURRENT_PRESET, "Default"))
        }
    }

    private fun onDeleteDataField(event: DeleteDataField) {
        dataFieldRepo.deleteDataField(event.value)
        updateDataFields()
    }

    private fun onRestoreDataField() {
        if (uiState.value.deletedDataField != null) {
            dataFieldRepo.addDataField(uiState.value.deletedDataField!!)
            updateDataFields()
        }
    }

    private fun onSaveDataField(event: SaveDataField) {
        viewModelScope.launch {
            try {
                dataFieldRepo.addDataField(event.value)
                _eventFlow.emit(SaveDataField("Data Field Saved: ${event.value.fieldName}"))
                _uiState.value = uiState.value.copy(
                    isAddDataFieldVisible = !uiState.value.isAddDataFieldVisible
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
        _uiState.value = uiState.value.copy(
            currentDataField = event.value,
            alertDialogState = AlertDialogState(
                title = String.format("Delete DataField: %s", event.value.fieldName),
                imageIcon = Icons.Default.Delete,
                body = "Are you sure you want to delete this Data Field?",
                onDismissRequest = { onDismissAlertDialog() },
                confirmButtonLabel = "Delete",
                confirmButtonOnClick = {
                    dataFieldRepo.deleteDataField(_uiState.value.currentDataField!!)
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
        _uiState.value = uiState.value.copy(
            isPresetDropDownMenuExpanded = true
        )
    }

    private fun onHidePresetDropdown() {
        _uiState.value = uiState.value.copy(
            isPresetDropDownMenuExpanded = false
        )
    }

    private fun onToggleAddNewDataField() {
        _uiState.value = uiState.value.copy(
            isAddDataFieldVisible = !uiState.value.isAddDataFieldVisible
        )
    }

    private fun onToggleMemberForm() {
        if (!uiState.value.isMemberFormVisible) {
            userPreferenceStore.setBoolean(Pair(SettingsKeys.SHOW_DASHBOARD_NAV_BAR, true))
        } else {
            userPreferenceStore.setBoolean(Pair(SettingsKeys.SHOW_DASHBOARD_NAV_BAR, false))
        }
        _uiState.value = uiState.value.copy(
            isMemberFormVisible = !uiState.value.isMemberFormVisible
        )
    }

    private fun updateDataFields() {
        _uiState.value = uiState.value.copy(
            dataFields = dataFieldRepo.getDataFieldsByPresetId(currentPreset.presetId)
        )
    }

    private fun updatePresetList() {
        _uiState.value = uiState.value.copy(
            presetList = presetRepo.getPresetList()
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

        dataFieldRepo.updateDataField(dataField)
        _uiState.value = uiState.value.copy(
            dataFields = dataFieldRepo.getDataFieldsByPresetId(currentPreset.presetId)
        )
    }

    private fun onRowToggle(event: ToggleRow) {
        dataField =
            uiState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.isEnabled = !dataField.isEnabled
    }

    private fun onEditFieldName(event: EditFieldName) {
        dataField =
            uiState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.fieldName = event.value
    }

    private fun onEditHintText(event: EditHintText) {
        dataField =
            uiState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.fieldHint = event.value
    }

    private fun onEditRowType(event: EditRowType) {
        dataField =
            uiState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.dataFieldType = DataFieldType.getByValue(event.value)
    }

    private fun onEditIsEnabled(event: EditIsEnabled) {
        dataField =
            uiState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.isEnabled = !dataField.isEnabled
    }

    private fun onEditFirstValue(event: EditFirstValue) {
        dataField =
            uiState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.first = event.value
    }

    private fun onEditSecondValue(event: EditSecondValue) {
        dataField =
            uiState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.second = event.value
    }

    private fun onEditThirdValue(event: EditThirdValue) {
        dataField =
            uiState.value.dataFields.first { dataField -> dataField.dataFieldId == event.index }
        dataField.third = event.value
    }

    fun onPresetEvent(event: PresetEvent) {
        when (event) {
            is ShowAddPresetDialog -> onShowAddPresetDialog()
            is ShowDeletePresetDialog -> onShowDeletePresetDialog(event)
            is ChangePreset -> onChangePreset(event)
            is EditPresetName -> onEditPresetName(event)
            is DeletePreset -> onDeletePreset()
            is PresetEvent.AddPreset -> saveNewPreset()
            is PresetEvent.DismissAlertDialog -> onDismissAlertDialog()
        }
    }

    private fun onDismissAlertDialog() {
        _uiState.value = uiState.value.copy(
            alertDialogState = null
        )
    }

    private fun onEditPresetName(event: EditPresetName) {
        val newPreset = Preset(0, event.value)
        _uiState.value = uiState.value.copy(
            newPreset = newPreset
        )
    }

    private fun onShowAddPresetDialog() {
        /*  _uiState.value = uiState.value.copy(
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
          )*/
        _uiState.value = uiState.value.copy(
            isPresetDropDownMenuExpanded = false,
            newPreset = null,
            alertDialogState = AlertDialogState(
                title = "Add New Preset",
                body = "Add a new Preset with the name",
                onDismissRequest = { onPresetEvent(PresetEvent.DismissAlertDialog) },
                confirmButtonLabel = "Add Preset",
                confirmButtonOnClick = {
                    saveNewPreset()
                    onPresetEvent(PresetEvent.DismissAlertDialog)
                },
                dismissButtonLabel = "Cancel",
                editFieldFunction = {
                    onPresetEvent(EditPresetName(it))
                },
                dismissButtonOnClick = { onPresetEvent(PresetEvent.DismissAlertDialog) },
                titleTextAlign = TextAlign.Center,
                dismissible = true
            )
        )
    }

    private fun saveNewPreset() {
        Log.i("Add Preset", "saveNewPreset: Add Preset clicked")
        val newPreset = _uiState.value.newPreset
        viewModelScope.launch {
            if (newPreset == null || newPreset.presetName.isBlank()) {
                _uiState.value = uiState.value.copy(
                    alertDialogState = _uiState.value.alertDialogState?.copy(
                        textFieldErrorText = "Enter Preset Name!"
                    )
                )
            } else {
                try {
                    presetRepo.addPreset(newPreset)
                    val presets = presetRepo.getPresetList()
                    val currentPreset = presetRepo.getPresetByPresetName(newPreset.presetName)
                    _uiState.value = uiState.value.copy(
                        alertDialogState = null,
                        isPresetDropDownMenuExpanded = false,
                        isAddDataFieldVisible = false,
                        presetList = presets,
                        currentPreset = newPreset,
                        dataFields = dataFieldRepo.getDataFieldsByPresetId(currentPreset.presetId),
                    )
                    userPreferenceStore.setString(Pair(CURRENT_PRESET, newPreset.presetName))
                    _eventFlow.emit(ShowSnackbar("Preset: ${newPreset.presetName} added!"))
                } catch (e: InvalidPresetException) {
                    _uiState.value = uiState.value.copy(
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
        _uiState.value = uiState.value.copy(
            modifiedPreset = event.value,
            alertDialogState = AlertDialogState(
                title = String.format("Delete Preset: %s", event.value.presetName),
                body = "Are you sure you want to delete this Preset?",
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
        val newPreset = presetRepo.getPresetByPresetName(event.value)

        userPreferenceStore.setString(Pair(CURRENT_PRESET, event.value))

        currentPreset = newPreset
        _uiState.value = uiState.value.copy(
            dataFields = dataFieldRepo.getDataFieldsByPresetId(newPreset.presetId),
            isPresetDropDownMenuExpanded = false,
            isAddDataFieldVisible = false,
            presetList = presetRepo.getPresetList(),
            currentPreset = newPreset,
            alertDialogState = null
        )
    }

    private fun onDeletePreset() {
        _uiState.value.modifiedPreset?.let { deletePresetActions(it) }
    }

    private fun deletePresetActions(preset: Preset) {
        val removeDataFields = dataFieldRepo.getDataFieldsByPresetId(preset.presetId)

        dataFieldRepo.deleteDataFields(removeDataFields)

        presetRepo.deletePreset(preset)

        val newPreset = presetRepo.getPresetByPresetName("Default")
        currentPreset = newPreset
        _uiState.value = uiState.value.copy(
            dataFields = dataFieldRepo.getDataFieldsByPresetId(newPreset.presetId),
            presetList = presetRepo.getPresetList(),
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
