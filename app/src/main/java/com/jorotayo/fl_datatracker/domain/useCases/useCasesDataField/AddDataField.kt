package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField

import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.InvalidDataFieldException
import com.jorotayo.fl_datatracker.domain.repository.DataFieldRepository

class AddDataField(
    private val repository: DataFieldRepository,
) {

    @Throws(InvalidDataFieldException::class)
    operator fun invoke(dataField: DataField) {
        var isError = false
        var msg = ""

        if (dataField.fieldName.isBlank()) {
            isError = true
            msg = "Please Enter Field Name for Data Field"
        } else if (repository.getDataFieldNames().contains(dataField.fieldName)) {
            isError = true
            msg = "Data Field Name '${dataField.fieldName}' already in use, please enter a new name"
        }

        if (dataField.dataFieldType == 2) {
            if (dataField.first.isBlank() || dataField.second.isBlank()) {
                isError = true
                msg = "Please Enter values for both fields"
            }
        } else if (dataField.dataFieldType == 6) {
            if (dataField.first.isBlank() || dataField.second.isBlank() || dataField.third.isBlank()) {
                isError = true
                msg = "Please Enter a value for all 3 Fields"
            }
        }
        if (isError) {
            throw InvalidDataFieldException("Invalid Data Field $msg")
        } else {
            repository.insertDataField(dataField)
        }
    }
}
