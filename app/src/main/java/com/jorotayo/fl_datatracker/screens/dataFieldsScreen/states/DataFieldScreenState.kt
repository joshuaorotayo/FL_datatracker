package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states

import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset

data class DataFieldScreenState(
    val dataFields: List<DataField> = emptyList(),
    val presetList: List<Preset> = emptyList(),
    val isAddDataFieldVisible: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val isAddPresetDialogVisible: Boolean = false,
    val isPresetDeleteDialogVisible: Boolean = false,
    val maxChar: Int = 30,
    val maxHintChar: Int = 60,
    val deletedDataField: DataField = DataField(
        presetId = 1,
        dataFieldId = 0
    ),
    val deletedPreset: Preset = Preset(
        presetId = 1,
        presetName = ""
    ),
    val currentPreset: Preset? = presetList[0],
    val isPresetDropDownMenuExpanded: Boolean = false
)
