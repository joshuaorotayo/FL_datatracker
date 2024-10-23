package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import com.jorotayo.fl_datatracker.domain.model.DataItem

data class
DataRowState(
    var dataItem: DataItem,
    var hasError: Boolean = false,
    var errorMsg: String = ""
)
