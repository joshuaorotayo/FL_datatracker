package com.jorotayo.fl_datatracker.domain.util.use_case

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataField_
import com.jorotayo.fl_datatracker.domain.model.InvalidDataFieldException
import io.objectbox.Box

class DataFieldUseCases {

    private val _dataFieldsBox2 = mutableStateOf(ObjectBox.get().boxFor(DataField::class.java))
    private var dataFieldsBox2: State<Box<DataField>> = _dataFieldsBox2

    private val fieldNames =
        dataFieldsBox2.value.query().build().property(DataField_.fieldName).findStrings().toList()

    @Throws(InvalidDataFieldException::class)
    fun addDataField(dataField: DataField) {
        var isError = false
        if (dataField.fieldName.isBlank()) {
            isError = true
            throw InvalidDataFieldException("The field name of the datafield cannot be empty")
        } else if (fieldNames.contains(dataField.fieldName)) {
            isError = true
            throw InvalidDataFieldException("Please enter a unique Data Field Name")
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
            if (dataField.dataList.isNullOrEmpty()) {
                dataField.dataList = listOf("", "", "")
            }
            dataField.fieldName = dataField.fieldName
            dataFieldsBox2.value.put(dataField)
        }
    }

}