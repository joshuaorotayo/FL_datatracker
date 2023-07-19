package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states

import com.jorotayo.fl_datatracker.domain.model.DataField

data class DataFieldRowState(
    val dataField: DataField,
    val isDropDownExpanded: Boolean = false,
//    var isHintVisible: Boolean = true
)
