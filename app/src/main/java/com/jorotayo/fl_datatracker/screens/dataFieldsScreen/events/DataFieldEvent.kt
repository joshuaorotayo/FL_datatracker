package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events

import com.jorotayo.fl_datatracker.domain.model.DataField

sealed class DataFieldEvent {
    object ToggleAddNewDataField : DataFieldEvent()
    object ExpandPresetDropdown : DataFieldEvent()
    object HidePresetDropdown : DataFieldEvent()
    object RestoreDeletedField : DataFieldEvent()
    data class ShowDeleteRowDialog(val value: DataField) : DataFieldEvent()
    data class SaveDataField(val value: DataField) : DataFieldEvent()
    data class DeleteDataField(val value: DataField) : DataFieldEvent()
}