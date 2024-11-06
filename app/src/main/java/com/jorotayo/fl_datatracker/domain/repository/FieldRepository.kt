package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.domain.model.DataField
import kotlinx.coroutines.flow.Flow

interface FieldRepository {

    fun addDataField(dataField: DataField)

    fun updateDataField(dataField: DataField): Long

    fun insertDataField(dataField: DataField): Long

    fun deleteDataField(dataField: DataField): Boolean

    fun deleteDataFields(dataFields: List<DataField>)

    fun getSubscribedDataFields(): Flow<MutableList<DataField>>

    fun getDataFieldsFlow(): Flow<DataField>

    fun getDataFields(): List<DataField>

    fun getDataFieldById(dataFieldId: Long): DataField

    fun getDataFieldsByPresetId(presetId: Long): List<DataField>

    fun getDataFieldsByPresetIdEnabled(presetId: Long): List<DataField>

    fun getDataFieldsByEnabled(): List<DataField>

    fun getDataFieldNames(): List<String>
}
