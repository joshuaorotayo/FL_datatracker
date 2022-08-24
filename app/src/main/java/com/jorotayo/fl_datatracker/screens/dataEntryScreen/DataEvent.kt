package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import com.jorotayo.fl_datatracker.domain.model.Data

sealed class DataEvent {
    data class SaveData(val value: Data) : DataEvent()
}
