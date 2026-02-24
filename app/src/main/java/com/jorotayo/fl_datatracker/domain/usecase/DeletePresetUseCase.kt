package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.data.repository.DataFieldRepository
import com.jorotayo.fl_datatracker.data.repository.PresetRepository

class DeletePresetUseCase(
    private val presetRepository: PresetRepository,
    private val fieldRepository: DataFieldRepository
) {
    /**
     * Deletes the preset and all of its associated data fields.
     * The "Default" preset cannot be deleted.
     */
    operator fun invoke(preset: Preset): Result<Unit> {
        if (preset.presetName.equals("Default", ignoreCase = true)) {
            return Result.failure(IllegalStateException("The Default preset cannot be deleted."))
        }
        fieldRepository.getFieldsForPreset(preset.presetId)
            .forEach { fieldRepository.deleteField(it.dataFieldId) }
        presetRepository.deletePreset(preset.presetId)
        return Result.success(Unit)
    }
}
