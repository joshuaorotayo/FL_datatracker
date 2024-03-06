package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events

import com.jorotayo.fl_datatracker.domain.model.Preset

sealed class PresetEvent {
    object DeletePreset : PresetEvent()
    object ShowAddPresetDialog : PresetEvent()
    object DismissAlertDialog : PresetEvent()
    data class ChangePreset(val value: String) : PresetEvent()
    data class EditPresetName(val value: String) : PresetEvent()
    data class ShowDeletePresetDialog(val value: Preset) : PresetEvent()
    data class AddPreset(val value: Preset) : PresetEvent()
}
