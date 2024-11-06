package com.jorotayo.fl_datatracker.domain.repositoryImpl

import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.repository.AppRepository
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository
import com.jorotayo.fl_datatracker.domain.repository.DataRepository
import com.jorotayo.fl_datatracker.domain.repository.FieldRepository
import com.jorotayo.fl_datatracker.domain.repository.PresetRepository
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState

class AppRepositoryImpl(
    private val fieldRepository: FieldRepository,
    private val dataItemRepository: DataItemRepository,
    private val dataRepository: DataRepository,
    private val presetRepository: PresetRepository
) : AppRepository {

    //Datafield Repository Methods
    override fun addDataField(dataField: DataField) = fieldRepository.addDataField(dataField)
    override fun insertDataField(dataField: DataField): Long =
        fieldRepository.insertDataField(dataField)

    override fun updateDataField(dataField: DataField): Long =
        fieldRepository.updateDataField(dataField)

    override fun deleteDataField(dataField: DataField): Boolean =
        fieldRepository.deleteDataField(dataField)

    override fun deleteDataFields(dataFields: List<DataField>) =
        fieldRepository.deleteDataFields(dataFields)

    override fun getDataFields(): List<DataField> = fieldRepository.getDataFields()
    override fun getDataFieldById(dataFieldId: Long): DataField =
        fieldRepository.getDataFieldById(dataFieldId)

    override fun getDataFieldsByPresetId(presetId: Long): List<DataField> =
        fieldRepository.getDataFieldsByPresetId(presetId)

    override fun getDataFieldsByPresetIdEnabled(presetId: Long): List<DataField> =
        fieldRepository.getDataFieldsByPresetIdEnabled(presetId)

    override fun getDataFieldsByEnabled(): List<DataField> =
        fieldRepository.getDataFieldsByEnabled()

    override fun getDataFieldNames(): List<String> = fieldRepository.getDataFieldNames()

    //Data Item Repository Methods
    override fun addDataItem(dataItem: DataItem): Long = dataItemRepository.addDataItem(dataItem)
    override fun removeDataItem(dataItem: DataItem): Boolean =
        dataItemRepository.removeDataItem(dataItem)

    override fun updateDataItem(dataItem: DataItem): Long =
        dataItemRepository.updateDataItem(dataItem)

    override fun getDataItemList(): List<DataItem> = dataItemRepository.getDataItemList()
    override fun getDataItemListByDataAndPresetId(dataId: Long, presetId: Long): List<DataItem> =
        dataItemRepository.getDataItemListByDataAndPresetId(dataId, presetId)

    override fun getDataItemById(dataItemId: Long): DataItem =
        dataItemRepository.getDataItemById(dataItemId)

    override fun getDataItemsListByDataId(dataId: Long): List<DataItem> =
        dataItemRepository.getDataItemsListByDataId(dataId)

    override fun getDataItemsByPresetId(dataPresetId: Long): List<DataItem> =
        dataItemRepository.getDataItemsByPresetId(dataPresetId)

    override fun getDataItemsEnabledByPresetId(dataPresetId: Long): List<DataItem> =
        dataItemRepository.getDataItemsEnabledByPresetId(dataPresetId)

    //Data Repository Methods
    override fun addData(data: Data): Long = dataRepository.addData(data)
    override fun updateData(data: Data): Long = dataRepository.updateData(data)
    override fun getData(): List<Data> = dataRepository.getData()
    override fun deleteData(data: Data): Boolean = dataRepository.deleteData(data)
    override fun deleteDataById(dataId: Long): Boolean = dataRepository.deleteDataById(dataId)
    override fun getDataByDataId(dataId: Long): Data = dataRepository.getDataByDataId(dataId)
    override fun getDataByDataName(dataName: String): Data? =
        dataRepository.getDataByDataName(dataName)

    override fun validateInsertDataForm(
        fieldNames: List<String>,
        dataForm: DataEntryScreenState,
    ): Pair<Boolean, DataEntryScreenState> =
        dataRepository.validateInsertDataForm(fieldNames, dataForm)

    //Preset Repository Methods
    override fun addPreset(preset: Preset) = presetRepository.addPreset(preset)
    override fun deletePreset(preset: Preset): Boolean = presetRepository.deletePreset(preset)
    override fun getPresetList(): List<Preset> = presetRepository.getPresetList()
    override fun getPresetById(presetId: Long): Preset = presetRepository.getPresetById(presetId)
    override fun getPresetByPresetName(presetName: String): Preset =
        presetRepository.getPresetByPresetName(presetName)

    override fun getCurrentPresetFromSettings(): Preset =
        presetRepository.getCurrentPresetFromSettings()
}