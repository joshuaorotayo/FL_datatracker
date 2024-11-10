package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states

import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset

data class DataFieldScreenState(
    val dataFields: List<DataField> = emptyList(),
    val presetList: List<Preset> = emptyList(),
    val isAddDataFieldVisible: Boolean = false,
    val isMemberFormVisible: Boolean = false,
    val maxChar: Int = 30,
    val maxHintChar: Int = 60,
    val deletedDataField: DataField? = null,
    val deletedPreset: Preset? = null,
    val newPreset: Preset? = null,
    val currentPreset: Preset? = null,
    val modifiedPreset: Preset? = null,
    val currentDataField: DataField? = null,
    val isPresetDropDownMenuExpanded: Boolean = false,
    val showAddPresetDialog: Boolean = false,
    val showDeletePresetDialog: Boolean = false,
    val textFieldError: Boolean = false,
    val showDeleteDataFieldDialog: Boolean = false
)
