package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import com.jorotayo.fl_datatracker.domain.model.DataField

data class DataRowState(
    var dataField: DataField,
    var hasError: Boolean = false,
)