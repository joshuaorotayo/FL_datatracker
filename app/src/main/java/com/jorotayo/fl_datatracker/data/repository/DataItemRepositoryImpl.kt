package com.jorotayo.fl_datatracker.data.repository

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.DataItem_
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository
import io.objectbox.Box

class DataItemRepositoryImpl : DataItemRepository {
    private val dataItemBox: Box<DataItem> = ObjectBox.boxStore().boxFor(DataItem::class.java)

    override fun getDataItemList(): List<DataItem> {
        return ObjectBox.boxStore().boxFor(DataItem::class.java).all.toList()
    }

    override fun getDataItemListByDataAndPresetId(dataId: Long, presetId: Long): List<DataItem> {
        return dataItemBox.query(
            DataItem_.dataId.equal(dataId).and(DataItem_.presetId.equal(presetId))
        ).build().find()
    }

    override fun getDataItemById(dataItemId: Long): DataItem {
        return dataItemBox.get(dataItemId)
    }

    override fun getDataItemsListByDataId(dataId: Long): List<DataItem> {
        return dataItemBox.query(DataItem_.dataId.equal(dataId)).build().find()
    }

    override fun getDataItemsByPresetId(dataPresetId: Long): List<DataItem> {
        return dataItemBox.query(DataItem_.presetId.equal(dataPresetId)).build().find()
    }

    override fun getDataItemsEnabledByPresetId(dataPresetId: Long): List<DataItem> {
        return dataItemBox.query(DataItem_.presetId.equal(dataPresetId)).build().find()
//            .and(DataItem_.isEnabled.equal(true)
    }

    override fun addDataItem(dataItem: DataItem) {
       dataItemBox.put(dataItem)
    }

    override fun removeDataItem(dataItem: DataItem) {
        dataItemBox.remove(dataItem)
    }

    override fun updateDataItem(dataItem: DataItem) {
        dataItemBox.put(dataItem)
    }
}