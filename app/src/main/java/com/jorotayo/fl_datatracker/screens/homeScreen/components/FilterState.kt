package com.jorotayo.fl_datatracker.screens.homeScreen.components

data class FilterState(
    val isFieldNameSearchEnabled: Boolean = true,
    val isFieldValueSearchEnabled: Boolean = false,
)

data class Filters(
    val filterName: String = "",
    val filterEnabled: Boolean = false
)

