package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

data class NewDataFieldState(
    val fieldName: String = "",
    val fieldHint: String? = "Hint for : $fieldName",
    val fieldType: Int = 0,
    val firstValue: String = "",
    val secondValue: String = "",
    val thirdValue: String = "",
    val editOptions: Boolean = false,
    val editHint: Boolean = false
)
