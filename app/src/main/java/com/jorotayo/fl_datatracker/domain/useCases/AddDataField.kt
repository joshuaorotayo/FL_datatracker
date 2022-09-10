package com.jorotayo.fl_datatracker.domain.useCases

import com.jorotayo.fl_datatracker.domain.data.Repository
import com.jorotayo.fl_datatracker.domain.model.DataField

class AddDataField(
    private val repository: Repository,
) {
    suspend operator fun invoke(dataField: DataField) {
        var isError = false
        var msg = ""

        if (dataField.fieldName.isBlank()) {
            isError = true
            msg = "Please Enter Field Name for Data Field"
        } else if (repository.getDataFieldNames()?.contains(dataField.fieldName) == true) {
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
        if (!isError) {

        }
    }
}
