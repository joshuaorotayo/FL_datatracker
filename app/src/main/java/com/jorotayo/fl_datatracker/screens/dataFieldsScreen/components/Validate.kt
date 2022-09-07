package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.*
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.util.capitaliseWord
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel
import io.objectbox.Box

class Validate {

    private val _dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)

    val settingBox: Box<Setting> = ObjectBox.get().boxFor(Setting::class.java)
    val presetBox: Box<Preset> = ObjectBox.get().boxFor(Preset::class.java)

    val currentPresetSetting =
        settingBox.query(Setting_.settingName.equal("currentPreset")).build()
            .findFirst()?.settingName

    val currentPresetId =
        currentPresetSetting?.let { Preset_.presetName.equal(it) }
            ?.let { presetBox.query(it).build().findFirst()?.presetId ?: 0 }

    private val fieldNames =
        _dataFieldsBox.query().build().property(DataField_.fieldName).findStrings().toList()

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
            dataField.presetId = currentPresetId!!.plus(1)
            viewModel.onDataEvent(DataFieldEvent.SaveDataField(dataField))
            msg = "Data Field '${dataField.fieldName}' saved!"
        }
        return Pair(isError, msg)
    }

    fun validatePreset(presetName: String): Boolean {
        val results = presetBox.query(Preset_.presetName.equal(presetName)).build().find()
        return results.size <= 0
    }
}