package com.jorotayo.fl_datatracker.domain.useCases

import com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset.*

data class PresetUseCases(
    val getPresetByPresetName: GetPresetByPresetName,
    val addPreset: AddPreset,
    val deletePreset: DeletePreset,
    val getPresetList: GetPresetList,
    val getPresetById: GetPresetById,
)
