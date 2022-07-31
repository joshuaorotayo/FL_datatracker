package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

sealed class DataFieldEvent {
    object ToggleAddNewDataField : DataFieldEvent()
    object SaveDataField : DataFieldEvent()
    data class AddFirstValue(val value: String) : DataFieldEvent()
    data class AddSecondValue(val value: String) : DataFieldEvent()
    data class AddThirdValue(val value: String) : DataFieldEvent()
    data class AddFieldName(val value: String) : DataFieldEvent()
    data class SelectFieldType(val value: Int) : DataFieldEvent()
}