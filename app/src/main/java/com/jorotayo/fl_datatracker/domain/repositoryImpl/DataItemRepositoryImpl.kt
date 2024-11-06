package com.jorotayo.fl_datatracker.domain.repositoryImpl

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.DataItem_
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository
import io.objectbox.Box
import javax.inject.Inject

class DataItemRepositoryImpl @Inject constructor() : DataItemRepository {
    private val dataItemBox: Box<DataItem> = ObjectBox.boxStore().boxFor(DataItem::class.java)

    override fun addDataItem(dataItem: DataItem): Long =
        dataItemBox.put(dataItem)

    override fun removeDataItem(dataItem: DataItem): Boolean =
        dataItemBox.remove(dataItem)

    override fun updateDataItem(dataItem: DataItem): Long =
        dataItemBox.put(dataItem)

    override fun getDataItemList(): List<DataItem> =
        dataItemBox.all

    override fun getDataItemListByDataAndPresetId(dataId: Long, presetId: Long): List<DataItem> =
        dataItemBox.query(
            DataItem_.dataId.equal(dataId).and(DataItem_.presetId.equal(presetId))
        ).build().find()

    override fun getDataItemById(dataItemId: Long): DataItem =
        dataItemBox.get(dataItemId)

    override fun getDataItemsListByDataId(dataId: Long): List<DataItem> =
        dataItemBox.query(DataItem_.dataId.equal(dataId)).build().find()

    override fun getDataItemsByPresetId(dataPresetId: Long): List<DataItem> =
        dataItemBox.query(DataItem_.presetId.equal(dataPresetId)).build().find()

    override fun getDataItemsEnabledByPresetId(dataPresetId: Long): List<DataItem> =
        dataItemBox.query(DataItem_.presetId.equal(dataPresetId)).build().find()

}
