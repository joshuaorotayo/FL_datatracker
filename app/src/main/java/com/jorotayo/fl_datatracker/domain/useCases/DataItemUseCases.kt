package com.jorotayo.fl_datatracker.domain.useCases

import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem.AddDataItem
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem.GetDataItemById
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem.GetDataItemList
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem.GetDataItemListByDataAndPresetId
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem.GetDataItemsByPresetId
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem.GetDataItemsEnabledByPresetId
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem.RemoveDataItem
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem.UpdateDataItem

data class DataItemUseCases(
    val addDataItem: AddDataItem,
    val removeDataItem: RemoveDataItem,
    val updateDataItem: UpdateDataItem,
    val getDataItemList: GetDataItemList,
    val getDataItemById: GetDataItemById,
    val getDataItemsByPresetId: GetDataItemsByPresetId,
    val getDataItemListByDataAndPresetId: GetDataItemListByDataAndPresetId,
    val getDataItemsEnabledByPresetId: GetDataItemsEnabledByPresetId,
)