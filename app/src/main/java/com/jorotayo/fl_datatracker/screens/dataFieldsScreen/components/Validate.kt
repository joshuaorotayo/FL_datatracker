package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset_
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.util.BoxState
import com.jorotayo.fl_datatracker.util.capitaliseWord
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel

class Validate {

    private val boxState = BoxState()

    // Filtered list of field names
    private val fieldNames: List<String> = boxState.filteredFields.map { it.fieldName }

    /***
     * Method used to validate and save data fields
     * @param dataField the datafield being validated and then added
     * @return Pair.first(Boolean) whether or not the method gave an error message (true means error occurred). Pair.second(String) String for the error message
     */
    fun validateDataField(
        dataField: DataField,
        viewModel: DataFieldsViewModel,
    ): Pair<Boolean, String> {
        var isError = false
        var msg = ""

        if (dataField.fieldName.isBlank()) {
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
            dataField.presetId = boxState.currentPreset?.presetId!!.plus(1)
            viewModel.onDataFieldEvent(DataFieldEvent.SaveDataField(dataField))
            msg = "Data Field '${dataField.fieldName}' saved!"
        }
        return Pair(isError, msg)
    }

    fun validatePreset(presetName: String): Boolean {
        val results =
            boxState.presetsBox.query(Preset_.presetName.equal(presetName)).build().find()
        return results.size <= 0
    }
}