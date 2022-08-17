package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

import com.jorotayo.fl_datatracker.domain.model.DataField

sealed class DataFieldEvent {
    object ToggleAddNewDataField : DataFieldEvent()
    data class OpenDeleteDialog(val dataField: DataField) : DataFieldEvent()
    data class ConfirmDelete(val dataField: DataField) : DataFieldEvent()

    //add operations for New Data Field
    data class AddFieldName(val value: String) : DataFieldEvent()
    data class SelectFieldType(val value: Int) : DataFieldEvent()
    data class AddHintText(val value: String) : DataFieldEvent()
    data class AddFirstValue(val value: String) : DataFieldEvent()
    data class AddSecondValue(val value: String) : DataFieldEvent()
    data class AddThirdValue(val value: String) : DataFieldEvent()

    // Edit operations for Row
    data class EditFieldName(val index: Long, val value: String) : DataFieldEvent()
    data class EditRowType(val index: Long, val value: Int) : DataFieldEvent()
    data class EditHintText(val index: Long, val value: String) : DataFieldEvent()
    data class EditStateValues(val index: Long, val valIndex: Int, val value: String) :
        DataFieldEvent()

    data class EditIsEnabled(val index: Long) : DataFieldEvent()
    object RestoreDeletedField : DataFieldEvent()
}