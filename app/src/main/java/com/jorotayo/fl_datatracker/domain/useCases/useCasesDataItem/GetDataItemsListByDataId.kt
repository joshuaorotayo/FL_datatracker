package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem

import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository

class GetDataItemsListByDataId(
    private val repository: DataItemRepository,
) {
    operator fun invoke(dataId: Long): List<DataItem> {
        return repository.getDataItemsListByDataId(dataId)
    }
}
