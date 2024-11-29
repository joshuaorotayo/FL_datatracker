package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states

data class NewDataFieldState(
    var dataFieldId: Long = 0,
    var presetId: Long = 0,
    var fieldName: String = "",
    var fieldHint: String = "",
    var fieldType: Int = 0,
    var firstValue: String = "",
    var secondValue: String = "",
    var thirdValue: String = "",
    var isEnabled: Boolean = true
)
