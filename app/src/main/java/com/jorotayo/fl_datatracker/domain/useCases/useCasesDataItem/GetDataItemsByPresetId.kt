package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem

import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository

class GetDataItemsByPresetId(
    private val repository: DataItemRepository,
) {
    operator fun invoke(dataPresetId: Long): List<DataItem> {
        return repository.getDataItemsByPresetId(dataPresetId)
    }
}