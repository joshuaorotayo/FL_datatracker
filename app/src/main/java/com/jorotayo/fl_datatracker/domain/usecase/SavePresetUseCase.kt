package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.data.repository.PresetRepository

class SavePresetUseCase(
    private val repository: PresetRepository
) {
    /**
     * Validates the name is not blank and not a duplicate before saving.
     * Returns the new preset's id on success.
     */
    operator fun invoke(name: String, existing: List<Preset>): Result<Long> {
        if (name.isBlank()) {
            return Result.failure(IllegalArgumentException("Preset name cannot be blank."))
        }
        if (existing.any { it.presetName.equals(name.trim(), ignoreCase = true) }) {
            return Result.failure(IllegalArgumentException("A preset named \"$name\" already exists."))
        }
        val id = repository.savePreset(Preset(presetName = name.trim()))
        return Result.success(id)
    }
}
