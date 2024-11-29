package com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset

import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.repository.PresetRepository

class GetPresetById(
    private val repository: PresetRepository,
) {
    operator fun invoke(presetId: Long): Preset {
        return repository.getPresetById(presetId)
    }
}
