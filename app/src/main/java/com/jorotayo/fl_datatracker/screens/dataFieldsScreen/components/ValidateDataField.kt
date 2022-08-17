package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataField_
import com.jorotayo.fl_datatracker.util.capitaliseWord
import io.objectbox.Box

class ValidateDataField {
    private val _dataFieldsBox2 = mutableStateOf(ObjectBox.get().boxFor(DataField::class.java))
    private var dataFieldsBox2: State<Box<DataField>> = _dataFieldsBox2

    private val fieldNames =
        dataFieldsBox2.value.query().build().property(DataField_.fieldName).findStrings().toList()

    /***
     * Method used to validate and save data fields
     * @param dataField the datafield being validated and then added
     * @return Pair.first(Boolean) whether or not the method gave an error mess
     */
    fun invoke(dataField: DataField): Pair<Boolean, String> {
        var isError = false
        var msg = ""

        if (dataField.fieldName.isEmpty()) {
            isError = true
            msg = "Please Enter Field Name for Data Field"
        } else if (fieldNames.contains(dataField.fieldName)) {
            isError = true
            msg = "Data Field Name '${dataField.fieldName}' already in use, please enter a new name"
        }

        if (dataField.dataFieldType == 2) {
            if (dataField.dataList[0].isBlank() || dataField.dataList[1]
                    .isBlank()
            ) {
                mutableListOf(dataField.dataList)
                isError = true
                msg = "Please Enter values for both fields"
            }
        } else if (dataField.dataFieldType == 6) {
            if (dataField.dataList[0].isBlank() || dataField.dataList[1]
                    .isBlank() || dataField.dataList[2].isBlank()
            ) {
                isError = true
                msg = "Please Enter a value for all 3 Fields"
            }
        }
        if (!isError) {
            if (dataField.dataList.isEmpty()) {
                dataField.dataList = listOf("", "", "")
            }
            dataField.fieldName = capitaliseWord(dataField.fieldName)
            dataFieldsBox2.value.put(dataField)
            msg = "Data Field '${dataField.fieldName}' saved!"
        }
        return Pair(isError, msg)
    }
}