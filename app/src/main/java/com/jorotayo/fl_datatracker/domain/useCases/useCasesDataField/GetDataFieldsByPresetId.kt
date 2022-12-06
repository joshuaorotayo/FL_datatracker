package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField

import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.repository.DataFieldRepository
import kotlinx.coroutines.flow.Flow

class GetDataFieldsByPresetId(
    private val repository: DataFieldRepository,
) {
    operator fun invoke(presetId: Long): Flow<DataField> {
        return repository.getDataFieldsByPresetId(presetId)
    }
}