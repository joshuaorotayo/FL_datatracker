package com.jorotayo.fl_datatracker.screens.homeScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.util.components.AlertDialogState

data class HomeScreenState(
    val isSearchVisible: Boolean = false,
    val text: String = "",
    val hint: String = "Search for data",
    val isHintVisible: Boolean = true,
    val isDeleteDialogVisible: MutableState<Boolean> = mutableStateOf(false),
    val dataList: List<Data> = ObjectBox.boxStore().boxFor(Data::class.java).all,
    val deletedItem: Data = Data(0, 0, "test", "yesterday", "today"),
    val alertDialogState: AlertDialogState? = null
)
