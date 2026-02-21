package com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen

import com.jorotayo.fl_datatracker.data.model.DataField
import com.jorotayo.fl_datatracker.data.model.Preset

sealed class DataFormEvent {
    data class SelectPreset(val preset: Preset) : DataFormEvent()
    data class SavePreset(val name: String) : DataFormEvent()
    data class DeletePreset(val preset: Preset) : DataFormEvent()
    data class AddField(val field: DataField) : DataFormEvent()
    data class SaveField(val field: DataField) : DataFormEvent()
    data class DeleteField(val field: DataField) : DataFormEvent()
}