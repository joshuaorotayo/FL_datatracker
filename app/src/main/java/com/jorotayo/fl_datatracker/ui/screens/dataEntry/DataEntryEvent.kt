package com.jorotayo.fl_datatracker.ui.screens.dataEntry

sealed class DataEntryEvent {
    object LoadFromPreference : DataEntryEvent()
    data class LoadRecord(val recordId: Long) : DataEntryEvent()
    data class UpdateRecordName(val name: String) : DataEntryEvent()
    data class UpdateValue(val fieldId: Long, val value: String) : DataEntryEvent()
    object EnableEditing : DataEntryEvent()
    object Submit : DataEntryEvent()
    object Clear : DataEntryEvent()
    object DismissToast : DataEntryEvent()
}