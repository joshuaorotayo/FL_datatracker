package com.jorotayo.fl_datatracker.domain.useCases

import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem.*

data class DataItemUseCases(
    val addDataItem: AddDataItem,
    val removeDataItem: RemoveDataItem,
    val updateDataItem: UpdateDataItem,
    val getDataItemList: GetDataItemList,
    val getDataItemById: GetDataItemById,
    val getDataItemsByPresetId: GetDataItemsByPresetId,
    val getDataItemsEnabledByPresetId: GetDataItemsEnabledByPresetId,
)