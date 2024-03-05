package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.DataItem_
import io.objectbox.Box
import javax.inject.Inject

class DataItemRepository @Inject constructor() {
    private val dataItemBox: Box<DataItem> = ObjectBox.boxStore().boxFor(DataItem::class.java)

    fun getDataItemList(): List<DataItem> =
        dataItemBox.all

    fun getDataItemListByDataAndPresetId(dataId: Long, presetId: Long): List<DataItem> =
        dataItemBox.query(
            DataItem_.dataId.equal(dataId).and(DataItem_.presetId.equal(presetId))
        ).build().find()

    fun getDataItemById(dataItemId: Long): DataItem =
        dataItemBox.get(dataItemId)

    fun getDataItemsListByDataId(dataId: Long): List<DataItem> =
        dataItemBox.query(DataItem_.dataId.equal(dataId)).build().find()

    fun getDataItemsByPresetId(dataPresetId: Long): List<DataItem> =
        dataItemBox.query(DataItem_.presetId.equal(dataPresetId)).build().find()

    fun getDataItemsEnabledByPresetId(dataPresetId: Long): List<DataItem> =
        dataItemBox.query(DataItem_.presetId.equal(dataPresetId)).build().find()

    fun addDataItem(dataItem: DataItem) =
        dataItemBox.put(dataItem)

    fun removeDataItem(dataItem: DataItem) =
        dataItemBox.remove(dataItem)

    fun updateDataItem(dataItem: DataItem) =
        dataItemBox.put(dataItem)
}
