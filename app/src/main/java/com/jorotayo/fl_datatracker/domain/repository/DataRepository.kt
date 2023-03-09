package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.domain.model.Data

interface DataRepository {

    fun getData(): List<Data>

    fun getDataByDataId(dataId: Long): Data

    fun getDataByDataName(dataName: String): Data?

    fun insertData(data: Data): Long

    fun deleteData(data: Data)

    fun deleteDataById(dataId: Long)

    fun updateData(data: Data)
}