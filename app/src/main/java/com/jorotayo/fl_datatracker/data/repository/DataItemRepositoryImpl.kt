package com.jorotayo.fl_datatracker.data.repository

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.DataItem_
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository
import io.objectbox.Box
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class DataItemRepositoryImpl : DataItemRepository {
    private val dataItemBox: Box<DataItem> = ObjectBox.get().boxFor(DataItem::class.java)

    override fun getDataItemList(): Flow<DataItem> {
        return dataItemBox.all.asFlow()
    }

    override fun getDataItemById(dataItemId: Long): DataItem {
        return dataItemBox.get(dataItemId)
    }

    override fun getDataItemsByPresetId(dataPresetId: Long): List<DataItem> {
        return dataItemBox.query(DataItem_.presetId.equal(dataPresetId)).build().find()
    }

    override fun getDataItemsEnabledByPresetId(dataPresetId: Long): List<DataItem> {
        return dataItemBox.query(DataItem_.presetId.equal(dataPresetId)
            .and(DataItem_.isEnabled.equal(true))).build().find()
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