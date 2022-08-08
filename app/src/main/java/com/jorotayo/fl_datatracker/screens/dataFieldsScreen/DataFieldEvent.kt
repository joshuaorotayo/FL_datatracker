package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

import com.jorotayo.fl_datatracker.domain.model.DataField

sealed class DataFieldEvent {
    object ToggleAddNewDataField : DataFieldEvent()
    data class SaveDataField(val dataField: DataField) : DataFieldEvent()
    data class SaveDataFieldName(val value: String) : DataFieldEvent()
    data class AddFirstValue(val value: String) : DataFieldEvent()
    data class AddSecondValue(val value: String) : DataFieldEvent()
    data class AddThirdValue(val value: String) : DataFieldEvent()
    data class SelectFieldType(val value: Int) : DataFieldEvent()
    data class EditDataField(val index: Int) : DataFieldEvent()
    data class EditRowName(val index: Long, val value: String) : DataFieldEvent()
    data class EditRowType(val index: Long, val value: Int) : DataFieldEvent()
    data class EditStateValues(val index: Long, val valIndex: Int, val value: String) :
        DataFieldEvent()

    data class CheckedChange(val index: Long) : DataFieldEvent()
}