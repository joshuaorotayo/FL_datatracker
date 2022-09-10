package com.jorotayo.fl_datatracker.screens.homeScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.jorotayo.fl_datatracker.domain.model.Data

data class HomeScreenState(
    var isSearchVisible: Boolean,
    var text: String,
    var hint: String,
    var isHintVisible: Boolean,
    var isDeleteDialogVisible: MutableState<Boolean> = mutableStateOf(false),
    var dataList: List<Data>,
    var deletedItem: Data,
)
