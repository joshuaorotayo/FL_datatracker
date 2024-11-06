package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.InvalidPresetException
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState

interface AppRepository {

    //Datafield Repository Methods

    fun addDataField(dataField: DataField)

    fun updateDataField(dataField: DataField): Long

    fun insertDataField(dataField: DataField): Long

    fun deleteDataField(dataField: DataField): Boolean

    fun deleteDataFields(dataFields: List<DataField>)

    fun getDataFields(): List<DataField>

    fun getDataFieldById(dataFieldId: Long): DataField

    fun getDataFieldsByPresetId(presetId: Long): List<DataField>

    fun getDataFieldsByPresetIdEnabled(presetId: Long): List<DataField>

    fun getDataFieldsByEnabled(): List<DataField>

    fun getDataFieldNames(): List<String>

    //Data Item Repository Methods

    fun addDataItem(dataItem: DataItem): Long

    fun removeDataItem(dataItem: DataItem): Boolean

    fun updateDataItem(dataItem: DataItem): Long

    fun getDataItemList(): List<DataItem>

    fun getDataItemListByDataAndPresetId(dataId: Long, presetId: Long): List<DataItem>

    fun getDataItemById(dataItemId: Long): DataItem

    fun getDataItemsListByDataId(dataId: Long): List<DataItem>

    fun getDataItemsByPresetId(dataPresetId: Long): List<DataItem>

    fun getDataItemsEnabledByPresetId(dataPresetId: Long): List<DataItem>

    //Data Repository Methods
    fun addData(data: Data): Long

    fun updateData(data: Data): Long

    fun getData(): List<Data>

    fun deleteData(data: Data): Boolean

    fun deleteDataById(dataId: Long): Boolean

    fun getDataByDataId(dataId: Long): Data

    fun getDataByDataName(dataName: String): Data?

    fun validateInsertDataForm(
        fieldNames: List<String>,
        dataForm: DataEntryScreenState,
    ): Pair<Boolean, DataEntryScreenState>

    //Preset Repository Methods

    @Throws(InvalidPresetException::class)
    fun addPreset(preset: Preset)

    fun deletePreset(preset: Preset): Boolean

    fun getPresetList(): List<Preset>

    fun getPresetById(presetId: Long): Preset

    fun getPresetByPresetName(presetName: String): Preset

    fun getCurrentPresetFromSettings(): Preset
}