package com.jorotayo.fl_datatracker.util

import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState
import java.util.*

fun capitaliseWord(word: String): String {
    return word.lowercase(Locale.ROOT)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

fun validateData(data: DataEntryScreenState): Pair<Boolean, DataEntryScreenState> {
    var noErrors = true
    val dataRows = data.dataRows

    if (data.dataName.isBlank()) {
        noErrors = false
        data.nameError = true

    }
    for (dr in dataRows) {
        if (dr.dataField.dataValue.isBlank()) {
            dr.hasError = true
            noErrors = false

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
    return Pair(noErrors, data)
}