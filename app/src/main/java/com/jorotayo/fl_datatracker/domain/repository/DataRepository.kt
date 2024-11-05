package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.Data_
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState
import io.objectbox.Box

class DataRepository {
    private val dataBox: Box<Data> = ObjectBox.boxStore().boxFor(Data::class.java)
    fun getData(): List<Data> = dataBox.all

    fun getDataByDataId(dataId: Long): Data = dataBox.get(dataId)

    fun getDataByDataName(dataName: String): Data? =
        dataBox.query(Data_.name.equal(dataName)).build().findFirst()

    fun addData(data: Data): Long = dataBox.put(data)

    fun deleteData(data: Data) = dataBox.remove(data)

    fun deleteDataById(dataId: Long) = dataBox.remove(dataId)

    fun updateData(data: Data) = dataBox.put(data)

    fun validateInsertDataForm(
        fieldNames: List<String>,
        dataForm: DataEntryScreenState,
    ): Pair<Boolean, DataEntryScreenState> {
        var newForm = dataForm
        var noErrors = true

        if (dataForm.dataName.isBlank()) {
            noErrors = false
            newForm = newForm.copy(
                nameError = true,
                nameErrorMsg = "Value missing for Meeting/Service name"
            )
        } else if (fieldNames.contains(dataForm.dataName)) {
            noErrors = false
            newForm = newForm.copy(
                nameError = true,
                nameErrorMsg = "Name already exists"
            )
        }
        for (dr in dataForm.dataRows) {
            if (dr.dataItem.dataValue.isBlank()) {
                dr.hasError = true
                noErrors = false
                when (dr.dataItem.dataFieldType.value) {
                    0 -> {
                        dr.errorMsg = "Please enter a value for ${dr.dataItem.fieldName}. "
                    }

                    1 -> {
                        dr.errorMsg = "Please enter a value for ${dr.dataItem.fieldName}. "
                    }

                    3 -> {
                        dr.errorMsg = "Please pick a Date for ${dr.dataItem.fieldName}. "
                    }

                    4 -> {
                        dr.errorMsg = "Please pick a Time for ${dr.dataItem.fieldName}. "
                    }
                }
            } else {
                dr.hasError = false
            }
        }

        return Pair(noErrors, newForm)
    }
}
