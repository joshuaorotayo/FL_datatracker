package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState

sealed class DataEvent {
    data class SaveData(val value: DataEntryScreenState) : DataEvent()
}
