package com.jorotayo.fl_datatracker.domain.util.use_case

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.InvalidDataFieldException
import io.objectbox.Box

class AddDataField {

    private val dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)

    @Throws(InvalidDataFieldException::class)
    suspend operator fun invoke(dataField: DataField) {
        if (dataField.fieldName.isBlank()) {
            throw InvalidDataFieldException("The field name of the datafield cannot be empty")
        } else {
            dataFieldsBox.put(dataField)
        }
    }
}