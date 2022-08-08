package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import com.jorotayo.fl_datatracker.domain.model.DataField

sealed class DataEvent {

    data class SaveDataField(val dataField: DataField) : DataEvent()
}
