package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem

import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository
import kotlinx.coroutines.flow.Flow

class GetDataItemList(
    private val repository: DataItemRepository,
) {
    operator fun invoke(): Flow<DataItem> {
        return repository.getDataItemList()
    }
}