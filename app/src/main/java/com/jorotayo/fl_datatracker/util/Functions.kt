package com.jorotayo.fl_datatracker.util

import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState
import java.util.*

fun capitaliseWord(word: String): String {
    return word.lowercase(Locale.ROOT)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

fun validateData(data: DataEntryScreenState): Pair<Boolean, DataEntryScreenState> {
    var hasErrors = false
    val dataRows = data.dataRows

    for (dr in dataRows) {
        if (dr.dataField.dataValue.isBlank()) {
            dr.hasError = true
            hasErrors = true

            when (dr.dataField.dataFieldType) {
                0 -> {
                    dr.errorMsg = "Please enter a value for ${dr.dataField.fieldName}"
                }
                3 -> {
                    dr.errorMsg = "Please pick a Date for ${dr.dataField.fieldName}"
                }
                4 -> {
                    dr.errorMsg = "Please pick a Time for ${dr.dataField.fieldName}"
                }
            }
        }
    }
    data.dataRows = dataRows
    return Pair(hasErrors, data)
}