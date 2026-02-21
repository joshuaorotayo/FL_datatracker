package com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen.components.DataFieldUi

sealed class DataFieldsEvent {
    data class SelectPreset(val preset: Preset) : DataFieldsEvent()
    data class SavePreset(val name: String) : DataFieldsEvent()
    data class DeletePreset(val preset: Preset) : DataFieldsEvent()
    data class SaveField(val field: DataFieldUi) : DataFieldsEvent()
    data class DeleteField(val field: DataFieldUi) : DataFieldsEvent()
    data class ToggleFieldActive(val field: DataFieldUi) : DataFieldsEvent()
    data class UpdateHint(val field: DataFieldUi, val hint: String) : DataFieldsEvent()
    data class UpdateBooleanOptions(val field: DataFieldUi, val options: List<String>) :
        DataFieldsEvent()

    data class UpdateTristateOptions(val field: DataFieldUi, val options: List<String>) :
        DataFieldsEvent()

    object AddField : DataFieldsEvent()
}
