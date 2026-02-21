package com.jorotayo.fl_datatracker.ui.screens.dataEntry

sealed class DataEntryEvent {
    data class LoadPreset(val presetId: Long) : DataEntryEvent()
    data class UpdateValue(val fieldId: Long, val value: String) : DataEntryEvent()
    object Submit : DataEntryEvent()
    object Clear : DataEntryEvent()
}