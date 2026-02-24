package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.DataField
import com.jorotayo.fl_datatracker.data.repository.DataFieldRepository

class GetFieldsForPresetUseCase(
    private val repository: DataFieldRepository
) {
    /**
     * Returns fields as UI state, ready for the composables.
     */
    operator fun invoke(presetId: Long): List<DataField> =
        repository.getFieldsForPreset(presetId)
}
