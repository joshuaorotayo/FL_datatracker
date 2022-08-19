package com.jorotayo.fl_datatracker.screens.homeScreen

import com.jorotayo.fl_datatracker.domain.model.Data

data class HomeScreenState(
    var isSearchVisible: Boolean,
    var text: String,
    var hint: String,
    var isHintVisible: Boolean,
    var dataItems: List<Data>
)
