package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.domain.model.DataField
import kotlinx.coroutines.flow.Flow

interface DataFieldRepository {

    fun getSubscribedDataFields(): Flow<MutableList<DataField>>

    fun getDataFieldsFlow(): Flow<DataField>

    fun getDataFields(): List<DataField>

    fun getDataFieldById(dataFieldId: Long): DataField

    fun getDataFieldsByPresetId(presetId: Long): Flow<DataField>

    fun getDataFieldsByEnabled(): Flow<DataField>

    fun getDataFieldNames(): List<String>

    fun insertDataField(dataField: DataField)

    fun deleteDataField(dataField: DataField)

    fun updateDataField(dataField: DataField)
}