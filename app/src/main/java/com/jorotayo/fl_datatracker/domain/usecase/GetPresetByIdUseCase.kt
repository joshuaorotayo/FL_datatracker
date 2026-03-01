package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.data.repository.PresetRepository
import javax.inject.Inject

/**
 * Fetches a single [Preset] by id.
 * Returns null if the preset no longer exists (e.g. was deleted after a
 * record was created against it).
 */
class GetPresetByIdUseCase @Inject constructor(
    private val repository: PresetRepository
) {
    operator fun invoke(presetId: Long): Preset? =
        repository.getPresetById(presetId)
}