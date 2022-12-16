package com.jorotayo.fl_datatracker.domain.useCases.useCasesData

import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState

class ValidateInsertDataForm {
    operator fun invoke(
        fieldNames: List<String>,
        dataForm: DataEntryScreenState,
    ): Pair<Boolean, DataEntryScreenState> {

        var noErrors = true

        if (dataForm.dataName.isBlank()) {
            dataForm.nameError = true
            noErrors = false
        }
        if (fieldNames.contains(dataForm.dataName)) {
            dataForm.nameError = true
            noErrors = false
        }
        for (dr in dataForm.dataRows) {
            if (dr.dataItem.dataValue.isBlank()) {
                dr.hasError = true
                noErrors = false
                when (dr.dataItem.dataFieldType) {
                    0 -> {
                        dr.errorMsg = "Please enter a value for ${dr.dataItem.fieldName}. "
                    }
                    1 -> {
                        dr.errorMsg = "Please enter a value for ${dr.dataItem.fieldName}. "
                    }
                    3 -> {
                        dr.errorMsg = "Please pick a Date for ${dr.dataItem.fieldName}. "
                    }
                    4 -> {
                        dr.errorMsg = "Please pick a Time for ${dr.dataItem.fieldName}. "
                    }
                }
            } else {
                dr.hasError = false
            }
        }
        return Pair(noErrors, dataForm)
    }
}