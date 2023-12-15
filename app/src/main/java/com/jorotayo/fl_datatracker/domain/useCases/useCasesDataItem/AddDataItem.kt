package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem

import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository

class AddDataItem(
    private val repository: DataItemRepository,
) {
    operator fun invoke(dataItem: DataItem) {
        return repository.addDataItem(dataItem)
    }
}
