package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

data class DataFieldRowState(
    var fieldName: String = "",
    var fieldHint: String? = "Hint for : $fieldName",
    var fieldType: Int = 0,
    var firstValue: String = "",
    var secondValue: String = "",
    var thirdValue: String = "",
    var isEnabled: Boolean,
)
