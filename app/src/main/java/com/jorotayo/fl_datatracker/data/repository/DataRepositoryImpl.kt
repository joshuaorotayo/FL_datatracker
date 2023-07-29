package com.jorotayo.fl_datatracker.data.repository

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.Data_
import com.jorotayo.fl_datatracker.domain.repository.DataRepository
import io.objectbox.Box

class DataRepositoryImpl : DataRepository {
    private val dataBox: Box<Data> = ObjectBox.boxStore().boxFor(Data::class.java)

    override fun getData(): List<Data> {
        return dataBox.all
    }

    override fun getDataByDataId(dataId: Long): Data {
        return dataBox.get(dataId)
    }

    override fun getDataByDataName(dataName: String): Data? {
        return dataBox.query(Data_.name.equal(dataName)).build().findFirst()
    }

    override fun insertData(data: Data): Long {
        return dataBox.put(data)
    }

    override fun updateData(data: Data) {
        dataBox.put(data)
    }

    override fun deleteData(data: Data) {
        dataBox.remove(data)
    }

    override fun deleteDataById(dataId: Long) {
        dataBox.remove(dataId)
    }
}