package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.domain.model.DataItem

interface DataItemRepository {

    fun addDataItem(dataItem: DataItem): Long

    fun removeDataItem(dataItem: DataItem): Boolean

    fun updateDataItem(dataItem: DataItem): Long

    fun getDataItemList(): List<DataItem>

    fun getDataItemListByDataAndPresetId(dataId: Long, presetId: Long): List<DataItem>

    fun getDataItemById(dataItemId: Long): DataItem

    fun getDataItemsListByDataId(dataId: Long): List<DataItem>

    fun getDataItemsByPresetId(dataPresetId: Long): List<DataItem>

    fun getDataItemsEnabledByPresetId(dataPresetId: Long): List<DataItem>
}