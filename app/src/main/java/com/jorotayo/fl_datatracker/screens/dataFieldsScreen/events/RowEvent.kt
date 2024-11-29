package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events

sealed class RowEvent {
    data class ToggleRow(val index: Long) : RowEvent()
    data class EditFieldName(val index: Long, val value: String) : RowEvent()
    data class EditRowType(val index: Long, val value: Int) : RowEvent()
    data class EditHintText(val index: Long, val value: String) : RowEvent()
    data class EditFirstValue(val index: Long, val value: String) : RowEvent()
    data class EditSecondValue(val index: Long, val value: String) : RowEvent()
    data class EditThirdValue(val index: Long, val value: String) : RowEvent()
    data class EditIsEnabled(val index: Long) : RowEvent()
}
