package com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset

import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.repository.PresetRepository

class GetPresetByPresetName(
    private val repository: PresetRepository,
) {
    operator fun invoke(presetName: String): Preset {
        return repository.getPresetByPresetName(presetName)
    }
}