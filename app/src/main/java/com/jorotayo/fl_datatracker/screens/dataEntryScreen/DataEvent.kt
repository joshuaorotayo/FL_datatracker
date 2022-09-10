package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState

sealed class DataEvent {
    data class SaveData(
        val dataEntryScreenState: DataEntryScreenState,
        val dataFieldList: List<DataField>,
    ) : DataEvent()

    data class SetName(val value: String) : DataEvent()
    data class SetDataValue(val value: String, val rowIndex: Int) : DataEvent()
    data class UpdateUiState(val value: DataEntryScreenState) : DataEvent()
}
