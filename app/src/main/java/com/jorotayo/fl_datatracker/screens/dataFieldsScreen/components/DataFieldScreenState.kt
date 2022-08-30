package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class DataFieldScreenState(
//    val dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java),
    val isAddDataFieldVisible: Boolean = false,
    val isDeleteDialogVisible: MutableState<Boolean> = mutableStateOf(false),
)
