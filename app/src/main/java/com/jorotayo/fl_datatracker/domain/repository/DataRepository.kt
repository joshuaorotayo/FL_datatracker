package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState

interface DataRepository {

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
}
