package com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState

data class DataFormState(
    val presets: List<Preset> = emptyList(),
    val selectedPreset: Preset? = null,
    val fields: List<DataFieldUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)