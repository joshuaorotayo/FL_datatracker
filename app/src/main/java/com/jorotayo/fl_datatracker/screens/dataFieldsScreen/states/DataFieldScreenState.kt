package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states

import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.util.components.AlertDialogState

data class DataFieldScreenState(
    val dataFields: List<DataField>,
    val presetList: List<Preset>,
    val isAddDataFieldVisible: Boolean = false,
    val isMemberFormVisible: Boolean = false,
    val maxChar: Int = 30,
    val maxHintChar: Int = 60,
    val deletedDataField: DataField? = null,
    val deletedPreset: Preset? = null,
    val newPreset: Preset? = null,
    val currentPreset: Preset,
    val modifiedPreset: Preset? = null,
    val currentDataField: DataField? = null,
    val isPresetDropDownMenuExpanded: Boolean = false,
    val alertDialogState: AlertDialogState? = null
)
