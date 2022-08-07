package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

sealed class DataFieldEvent {
    object ToggleAddNewDataField : DataFieldEvent()
    object SaveDataField : DataFieldEvent()
    data class SaveDataFieldName(val value: String) : DataFieldEvent()
    data class AddFirstValue(val value: String) : DataFieldEvent()
    data class AddSecondValue(val value: String) : DataFieldEvent()
    data class AddThirdValue(val value: String) : DataFieldEvent()
    data class SelectFieldType(val value: Int) : DataFieldEvent()
    data class EditDataField(val index: Int) : DataFieldEvent()
    data class EditRowName(val index: Long, val value: String) : DataFieldEvent()
    data class EditRowType(val index: Long, val value: Int) : DataFieldEvent()
    data class CheckedChange(val index: Long) : DataFieldEvent()
}