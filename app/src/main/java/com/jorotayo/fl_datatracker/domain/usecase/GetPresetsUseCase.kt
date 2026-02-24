package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.data.repository.PresetRepository

class GetPresetsUseCase(
    private val repository: PresetRepository
) {
    operator fun invoke(): List<Preset> =
        repository.getAllPresets()
}
