package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events

import com.jorotayo.fl_datatracker.domain.model.DataField

sealed class DataFieldEvent {
    object InitScreen : DataFieldEvent()
    object ToggleAddNewDataField : DataFieldEvent()
    object ExpandPresetDropdown : DataFieldEvent()
    object DismissPresetDropdown : DataFieldEvent()
    object RestoreDeletedField : DataFieldEvent()
    object ToggleMemberForm : DataFieldEvent()
    object DismissDeleteDataFieldDialog : DataFieldEvent()
    object ConfirmDeleteDataField : DataFieldEvent()

    data class ShowDeleteDataFieldDialog(val value: DataField) : DataFieldEvent()
    data class SaveDataField(val value: DataField) : DataFieldEvent()
}
