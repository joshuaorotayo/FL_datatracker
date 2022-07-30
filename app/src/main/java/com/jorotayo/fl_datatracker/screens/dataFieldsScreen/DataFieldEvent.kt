package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

sealed class DataFieldEvent {
    object ToggleAddNewDataField : DataFieldEvent()
    object SaveDataField : DataFieldEvent()
    data class TriAddFirstValue(val value: String) : DataFieldEvent()
    data class TriAddSecondValue(val value: String) : DataFieldEvent()
    data class TriAddThirdValue(val value: String) : DataFieldEvent()
    data class PairAddFirstValue(val value: String) : DataFieldEvent()
    data class PairAddSecondValue(val value: String) : DataFieldEvent()
}