package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.domain.model.DataItem
import kotlinx.coroutines.flow.Flow

interface DataItemRepository {

    fun getDataItemList(): Flow<DataItem>

    fun getDataItemById(dataItemId: Long): DataItem

    fun getDataItemsByPresetId(dataPresetId: Long): List<DataItem>

    fun getDataItemsEnabledByPresetId(dataPresetId: Long): List<DataItem>

    fun addDataItem(dataItem: DataItem)

    fun removeDataItem(dataItem: DataItem)

    fun updateDataItem(dataItem: DataItem)

}