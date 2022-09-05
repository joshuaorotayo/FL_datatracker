package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.jorotayo.fl_datatracker.domain.model.DataField

data class DataFieldScreenState(
    val isAddDataFieldVisible: Boolean = false,
    val isDeleteDialogVisible: MutableState<Boolean> = mutableStateOf(false),
    val isAddPresetDialogVisible: MutableState<Boolean> = mutableStateOf(false),
    val maxChar: Int = 30,
    val maxHintChar: Int = 60,
    var deletedDataField: DataField = DataField(
        presetId = 1,
        dataFieldId = 0
    ),
)
