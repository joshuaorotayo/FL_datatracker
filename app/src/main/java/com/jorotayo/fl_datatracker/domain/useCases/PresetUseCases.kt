package com.jorotayo.fl_datatracker.domain.useCases

import com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset.AddPreset
import com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset.DeletePreset
import com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset.GetCurrentPresetFromSettings
import com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset.GetPresetById
import com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset.GetPresetByPresetName
import com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset.GetPresetList

data class PresetUseCases(
    val getPresetByPresetName: GetPresetByPresetName,
    val addPreset: AddPreset,
    val deletePreset: DeletePreset,
    val getPresetList: GetPresetList,
    val getPresetById: GetPresetById,
    val getCurrentPresetFromSettings: GetCurrentPresetFromSettings,
)
