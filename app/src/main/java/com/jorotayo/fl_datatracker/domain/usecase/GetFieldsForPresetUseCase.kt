package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.repository.DataFieldRepository
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState
import com.jorotayo.fl_datatracker.domain.util.toUiStateList

class GetFieldsForPresetUseCase(
    private val repository: DataFieldRepository
) {
    /**
     * Returns fields as UI state, ready for the composables.
     */
    operator fun invoke(presetId: Long): List<DataFieldUiState> =
        repository.getFieldsForPreset(presetId).toUiStateList()
}
