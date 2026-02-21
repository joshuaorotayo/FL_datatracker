package com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen.components.DataFieldUi

data class DataFieldsState(
    val presets: List<Preset> = emptyList(),
    val selectedPreset: Preset? = null,
    val fields: List<DataFieldUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)