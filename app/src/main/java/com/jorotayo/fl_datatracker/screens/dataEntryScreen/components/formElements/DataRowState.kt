package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import com.jorotayo.fl_datatracker.domain.model.DataItem

data class DataRowState(
    var dataItem: DataItem,
    val hasError: Boolean = false,
    val errorMsg: String = ""
)