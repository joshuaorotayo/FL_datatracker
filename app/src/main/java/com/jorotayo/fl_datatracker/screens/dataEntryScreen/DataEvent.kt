package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState

sealed class DataEvent {
    data class SaveData(val dataEntryScreenState: DataEntryScreenState) : DataEvent()
    data class SetName(val value: String) : DataEvent()
    data class SetDataValue(val value: String, val rowIndex: Int) : DataEvent()
    data class UpdateUiState(val value: DataEntryScreenState) : DataEvent()

    data class UpdateDataId(val value: Long) : DataEvent()
}
