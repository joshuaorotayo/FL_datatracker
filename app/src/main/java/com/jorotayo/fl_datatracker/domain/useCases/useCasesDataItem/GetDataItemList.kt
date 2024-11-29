package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem

import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository

class GetDataItemList(
    private val repository: DataItemRepository,
) {
    operator fun invoke(): List<DataItem> {
        return repository.getDataItemList()
    }
}
