package com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset

import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.repository.PresetRepository

class AddPreset(
    private val repository: PresetRepository,
) {
    operator fun invoke(preset: Preset) {
        return repository.addPreset(preset)
    }
}