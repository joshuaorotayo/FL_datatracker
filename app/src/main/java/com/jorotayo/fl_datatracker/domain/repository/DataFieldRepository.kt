package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataField_
import com.jorotayo.fl_datatracker.domain.model.InvalidDataFieldException
import com.jorotayo.fl_datatracker.domain.util.DataFieldType.*
import io.objectbox.Box
import io.objectbox.android.AndroidScheduler
import io.objectbox.kotlin.toFlow
import io.objectbox.query.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class DataFieldRepository @Inject constructor() {
    private val dataFieldBox: Box<DataField> = ObjectBox.boxStore().boxFor(DataField::class.java)
    private val dataFieldQuery: Query<DataField> =
        dataFieldBox.query(DataField_.isEnabled.equal(true)).build()

    fun getSubscribedDataFields(): Flow<MutableList<DataField>> =
        dataFieldQuery.subscribe().on(AndroidScheduler.mainThread()).toFlow()

    fun getDataFieldsFlow(): Flow<DataField> =
        dataFieldBox.all.asFlow()

    fun getDataFields(): List<DataField> =
        dataFieldBox.all

    fun getDataFieldById(dataFieldId: Long): DataField =
        dataFieldBox.get(dataFieldId)

    fun getDataFieldsByPresetId(presetId: Long): List<DataField> =
        dataFieldBox.query(DataField_.presetId.equal(presetId)).build().find()

    fun getDataFieldsByPresetIdEnabled(presetId: Long): List<DataField> =
        dataFieldBox.query(
            DataField_.isEnabled.equal(true).and(DataField_.presetId.equal(presetId))
        ).build().find()

    fun getDataFieldsByEnabled(): List<DataField> =
        dataFieldBox.query(DataField_.isEnabled.equal(true)).build().find()

    fun getDataFieldNames(): List<String> =
        dataFieldBox.query().build().property(DataField_.fieldName).findStrings().asList()

    fun insertDataField(dataField: DataField): Long =
        dataFieldBox.put(dataField)

    fun deleteDataField(dataField: DataField) =
        dataFieldBox.remove(dataField)

    fun deleteDataFields(dataFields: List<DataField>) =
        dataFieldBox.remove(dataFields)

    fun updateDataField(dataField: DataField) =
        dataFieldBox.put(dataField)

    @Throws(InvalidDataFieldException::class)
    fun addDataField(dataField: DataField) {
        var isError = false
        var msg = ""

        if (dataField.fieldName.isBlank()) {
            isError = true
            msg = "Please Enter Field Name for Data Field"
        } else if (getDataFieldNames().contains(dataField.fieldName)) {
            isError = true
            msg = "Data Field Name '${dataField.fieldName}' already in use, please enter a new name"
        }

        if (dataField.dataFieldType == BOOLEAN) {
            if (dataField.first.isBlank() || dataField.second.isBlank()) {
                isError = true
                msg = "Please Enter values for both fields"
            }
        }
        if (dataField.dataFieldType == TRISTATE) {
            if (dataField.first.isBlank() || dataField.second.isBlank() || dataField.third.isBlank()) {
                isError = true
                msg = "Please Enter a value for all 3 Fields"
            }
        }
        if (isError) {
            throw InvalidDataFieldException("Invalid Data Field. $msg")
        } else {
            dataFieldBox.put(dataField)
        }
    }
}
