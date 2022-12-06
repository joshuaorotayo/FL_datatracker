package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem

import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository

class GetDataItemById(
    private val repository: DataItemRepository,
) {
    operator fun invoke(dataItemId: Long): DataItem {
        return repository.getDataItemById(dataItemId)
    }
}