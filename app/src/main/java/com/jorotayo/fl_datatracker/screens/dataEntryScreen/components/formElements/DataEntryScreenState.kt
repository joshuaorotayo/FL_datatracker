package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

data class DataEntryScreenState(
    var dataName: String,
    var dataRows: MutableList<DataRowState>,
    var nameError: Boolean,
)
