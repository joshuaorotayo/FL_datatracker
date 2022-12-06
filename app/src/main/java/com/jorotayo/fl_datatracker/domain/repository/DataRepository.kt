package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.domain.model.Data
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    fun getData(): Flow<Data>

    fun getDataByDataId(dataId: Long): Data

    fun getDataByDataName(dataName: String): Data?

    fun insertData(data: Data)

    fun deleteData(data: Data)

    fun deleteDataById(dataId: Long)

    suspend fun updateData(data: Data)
}