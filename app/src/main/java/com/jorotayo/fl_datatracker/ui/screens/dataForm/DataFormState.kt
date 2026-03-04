package com.jorotayo.fl_datatracker.ui.screens.dataForm

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.DataFieldUi

data class DataFormState(
    val presets: List<Preset> = emptyList(),
    val selectedPreset: Preset? = null,
    val fields: List<DataFieldUi> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showDeleteFieldDialog: Boolean = false,
    val fieldToDelete: DataFieldUi? = null,
    val showDeletePresetDialog: Boolean = false,
    val presetToDelete: Preset? = null,
    val toast: AppToastData? = null,
    val showAddFieldSheet: Boolean = false
)
