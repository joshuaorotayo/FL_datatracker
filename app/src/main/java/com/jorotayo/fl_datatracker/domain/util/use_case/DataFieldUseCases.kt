package com.jorotayo.fl_datatracker.domain.util.use_case

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.InvalidDataFieldException
import io.objectbox.Box

class DataFieldUseCases {

    private val dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)

    @Throws(InvalidDataFieldException::class)
    fun addDataField(dataField: DataField) {
        var isError = false
        if (dataField.fieldName.isBlank()) {
            isError = true
            throw InvalidDataFieldException("The field name of the datafield cannot be empty")
        }
        if (dataField.dataFieldType == 2) {
            if (dataField.dataList?.get(0)?.isBlank() == true || dataField.dataList?.get(1)
                    ?.isBlank() == true
            ) {
                isError = true
                throw InvalidDataFieldException("Please enter a value for both options")
            }
        }
        if (dataField.dataFieldType == 6) {
            if (dataField.dataList?.get(0)?.isBlank() == true || dataField.dataList?.get(1)
                    ?.isBlank() == true
                || dataField.dataList?.get(2)?.isBlank() == true
            ) {
                isError = true
                throw InvalidDataFieldException("Please enter a value for all 3 options")
            }
        }
        if (!isError) {
            dataFieldsBox.put(dataField)
        }
    }

}