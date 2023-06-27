package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events

import com.jorotayo.fl_datatracker.domain.model.DataField

sealed class DataFieldEvent {
    object ToggleAddNewDataField : DataFieldEvent()
    object TogglePresetDeleteDialog : DataFieldEvent()
    object ToggleAddPresetDialog : DataFieldEvent()
    object ToggleDeleteRowDialog : DataFieldEvent()
    object ExpandPresetDropdown : DataFieldEvent()
    object HidePresetDropdown : DataFieldEvent()
    object RestoreDeletedField : DataFieldEvent()
    data class OpenDeleteDialog(val dataField: DataField) : DataFieldEvent()
    data class AddFieldName(val value: String) : DataFieldEvent()
    data class SelectFieldType(val value: Int) : DataFieldEvent()
    data class AddHintText(val value: String) : DataFieldEvent()
    data class AddFirstValue(val value: String) : DataFieldEvent()
    data class AddSecondValue(val value: String) : DataFieldEvent()
    data class AddThirdValue(val value: String) : DataFieldEvent()
    data class SaveDataField(val value: DataField) : DataFieldEvent()
    data class DeleteDataField(val value: DataField) : DataFieldEvent()
}