package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import com.jorotayo.fl_datatracker.domain.model.Preset

data class DataEntryScreenState(
    val dataName: String = "",
    val dataRows: MutableList<DataRowState> = mutableListOf(),
    val nameError: Boolean = false,
    val nameErrorMsg: String = "",
    val formSubmitted: Boolean = false,
    val currentImageIndex: Int = 0,
    val presetSetting: Preset = Preset(0, "Default")
)
