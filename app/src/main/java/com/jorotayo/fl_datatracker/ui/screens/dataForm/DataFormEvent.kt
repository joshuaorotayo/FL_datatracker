package com.jorotayo.fl_datatracker.ui.screens.dataForm

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.DataFieldUi
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.FieldUpdate

sealed class DataFormEvent {
    // Preset management
    data class SelectPreset(val preset: Preset) : DataFormEvent()
    data class SavePreset(val name: String) : DataFormEvent()
    data class DeletePreset(val preset: Preset) : DataFormEvent()

    // Field management
    object ToggleAddFieldSheet : DataFormEvent()
    data class UpdateField(val field: DataFieldUi, val update: FieldUpdate) : DataFormEvent()

    data class RequestDeleteField(val field: DataFieldUi) : DataFormEvent()
    object ConfirmDeleteField : DataFormEvent()
    data object DismissDeleteDialog : DataFormEvent()

    data class SaveField(val field: DataFieldUi) : DataFormEvent()
    object DismissToast : DataFormEvent()
    data class ConfirmDeletePreset(val preset: Preset) : DataFormEvent()
    object DismissDeletePresetDialog : DataFormEvent()
}
