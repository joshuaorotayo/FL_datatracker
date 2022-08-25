package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState

sealed class DataEvent {
    data class SaveData(val value: Data) : DataEvent()
    data class SetName(val value: String) : DataEvent()
    data class UpdateUiState(val value: DataEntryScreenState) : DataEvent()
}
