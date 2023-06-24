package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events

import com.jorotayo.fl_datatracker.domain.model.Preset

sealed class PresetEvent {
    object ToggleAddPresetDialog : PresetEvent()
    object TogglePresetDeleteDialog : PresetEvent()
    data class ChangePreset(val value: String) : PresetEvent()
    data class AddPreset(val value: String) : PresetEvent()
    data class DeletePreset(val value: Preset) : PresetEvent()
}