package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataField_
import com.jorotayo.fl_datatracker.util.capitaliseWord
import io.objectbox.Box

class Validate {
    private val _dataFieldsBox2 = mutableStateOf(ObjectBox.get().boxFor(DataField::class.java))
    private var dataFieldsBox2: State<Box<DataField>> = _dataFieldsBox2

    private val fieldNames =
        dataFieldsBox2.value.query().build().property(DataField_.fieldName).findStrings().toList()

    /***
     * Method used to validate and save data fields
     * @param dataField the datafield being validated and then added
     * @return Pair.first(Boolean) whether or not the method gave an error mess
     */
    fun validateDataField(dataField: DataField): Pair<Boolean, String> {
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
            dataField.fieldName = capitaliseWord(dataField.fieldName)
            dataFieldsBox2.value.put(dataField)
            msg = "Data Field '${dataField.fieldName}' saved!"
        }
        return Pair(isError, msg)
    }

    fun validateData(data: Data): Data {
        val dataFields = data.dataFields
        /*   for (df in dataFields) {
               when (df.dataFieldType) {
                   0 -> {
                       df.
                   }
               }
           }*/
        return data
    }
}