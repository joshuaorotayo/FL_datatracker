package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

sealed class DataEntryEvent {
    object ToggleAddNewDataField : DataEntryEvent()
}