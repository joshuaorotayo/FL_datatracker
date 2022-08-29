package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.*
import com.jorotayo.fl_datatracker.util.capitaliseWord
import io.objectbox.Box

class Validate {
    private val _dataFieldsBox2 = mutableStateOf(ObjectBox.get().boxFor(DataField::class.java))
    private var dataFieldsBox2: State<Box<DataField>> = _dataFieldsBox2

    val settingBox: Box<Setting> = ObjectBox.get().boxFor(Setting::class.java)
    val presetBox: Box<Preset> = ObjectBox.get().boxFor(Preset::class.java)

    val currentPreset =
        settingBox.query(Setting_.settingName.equal("currentPreset")).build().findFirst()

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
            if (currentPreset != null) dataField.presetId =
                currentPreset.Id else dataField.presetId = 0
            dataFieldsBox2.value.put(dataField)
            msg = "Data Field '${dataField.fieldName}' saved!"
        }
        return Pair(isError, msg)
    }
}