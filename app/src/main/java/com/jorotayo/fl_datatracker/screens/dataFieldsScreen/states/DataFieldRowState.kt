package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states

data class DataFieldRowState(
    var id: Long = 0,
    var fieldName: String = "",
    var fieldHint: String? = fieldName,
    var fieldType: Int = 0,
    var firstValue: String = "",
    var secondValue: String = "",
    var thirdValue: String = "",
    var isEnabled: Boolean,
    var presetId: Long = 0,
)
