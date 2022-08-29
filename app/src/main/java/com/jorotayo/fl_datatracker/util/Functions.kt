package com.jorotayo.fl_datatracker.util

import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState
import java.text.SimpleDateFormat
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
        if (dr.dataItem.dataValue.isBlank()) {
            dr.hasError = true
            noErrors = false

            when (dr.dataItem.dataField.dataFieldType) {
                0 -> {
                    dr.errorMsg = "Please enter a value for ${dr.dataItem.dataField.fieldName}"
                }
                3 -> {
                    dr.errorMsg = "Please pick a Date for ${dr.dataItem.dataField.fieldName}"
                }
                4 -> {
                    dr.errorMsg = "Please pick a Time for ${dr.dataItem.dataField.fieldName}"
                }
            }
        }
    }
    data.dataRows = dataRows
    return Pair(noErrors, data)
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}