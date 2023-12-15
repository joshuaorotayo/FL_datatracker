package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField

import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.repository.DataFieldRepository

class GetDataFieldsByPresetIdEnabled(
    private val repository: DataFieldRepository,
) {
    operator fun invoke(presetId: Long): List<DataField> {
        return repository.getDataFieldsByPresetIdEnabled(presetId)
    }
}
