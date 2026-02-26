package com.jorotayo.fl_datatracker.ui.screens.dataForm.components

sealed interface FieldUpdate {
    data class Hint(val value: String) : FieldUpdate
    data class BooleanOptions(val options: List<String>) : FieldUpdate
    data class TristateOptions(val options: List<String>) : FieldUpdate
    data class Type(val type: FieldType) : FieldUpdate
    data object ToggleActive : FieldUpdate
}
