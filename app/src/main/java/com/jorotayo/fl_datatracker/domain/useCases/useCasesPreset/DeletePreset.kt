package com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset

import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.repository.PresetRepository

class DeletePreset(
    private val repository: PresetRepository,
) {
    suspend operator fun invoke(preset: Preset) {
        return repository.deletePreset(preset)
    }
}