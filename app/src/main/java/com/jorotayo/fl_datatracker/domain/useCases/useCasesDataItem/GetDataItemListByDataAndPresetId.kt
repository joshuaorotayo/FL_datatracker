package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem

import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository

class GetDataItemListByDataAndPresetId(
    private val repository: DataItemRepository,
) {
    operator fun invoke(dataId: Long, presetId: Long): List<DataItem> {
        return repository.getDataItemListByDataAndPresetId(dataId, presetId)
    }
}
