package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem

import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository

class RemoveDataItem(
    private val repository: DataItemRepository,
) {
    suspend operator fun invoke(dataItem: DataItem) {
        return repository.removeDataItem(dataItem)
    }
}