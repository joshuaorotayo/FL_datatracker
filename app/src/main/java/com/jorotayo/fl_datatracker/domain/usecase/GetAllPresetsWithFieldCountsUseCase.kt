package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.data.repository.DataFieldRepository
import com.jorotayo.fl_datatracker.data.repository.PresetRepository
import javax.inject.Inject

class GetAllPresetsWithFieldCountsUseCase @Inject constructor(
    private val presetRepo: PresetRepository,
    private val fieldRepo: DataFieldRepository
) {
    suspend operator fun invoke(): Pair<List<Preset>, Set<Long>> {
        val presets = presetRepo.getAllPresets()
        val withFields = presets
            .filter { fieldRepo.getFieldsForPreset(it.presetId).isNotEmpty() }
            .map { it.presetId }
            .toSet()
        return presets to withFields
    }
}