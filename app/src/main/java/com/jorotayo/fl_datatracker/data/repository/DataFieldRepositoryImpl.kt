package com.jorotayo.fl_datatracker.data.repository

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataField_
import com.jorotayo.fl_datatracker.domain.repository.DataFieldRepository
import io.objectbox.Box
import io.objectbox.android.AndroidScheduler
import io.objectbox.kotlin.toFlow
import io.objectbox.query.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class DataFieldRepositoryImpl : DataFieldRepository {
    private val dataFieldBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)

    @OptIn(ExperimentalCoroutinesApi::class)// Listen to all changes to a Box
    val flow = dataFieldBox.store.subscribe(DataField::class.java).toFlow()

    private val dataFieldQuery: Query<DataField> =
        dataFieldBox.query(DataField_.isEnabled.equal(true)).build()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSubscribedDataFields(): Flow<MutableList<DataField>> {
        return dataFieldQuery.subscribe().on(AndroidScheduler.mainThread()).toFlow()
    }

    override fun getDataFieldsFlow(): Flow<DataField> {
        return dataFieldBox.all.asFlow()
    }

    override fun getDataFields(): List<DataField> {
        return ObjectBox.get().boxFor(DataField::class.java).all.toList()
    }

    override fun getDataFieldById(dataFieldId: Long): DataField {
        return dataFieldBox.get(dataFieldId)
    }

    override fun getDataFieldsByPresetId(presetId: Long): Flow<DataField> {
        return dataFieldBox.query(DataField_.presetId.equal(presetId)).build().find().asFlow()
    }

    override fun getDataFieldsByEnabled(): Flow<DataField> {
        return dataFieldBox.query(DataField_.isEnabled.equal(true)).build().find().asFlow()
    }

    override fun getDataFieldNames(): List<String> {
        return dataFieldBox.query().build().property(DataField_.fieldName).findStrings().asList()
    }

    override fun insertDataField(dataField: DataField) {
        dataFieldBox.put(dataField)
    }

    override fun deleteDataField(dataField: DataField) {
        dataFieldBox.remove(dataField)
    }

    override fun updateDataField(dataField: DataField) {
        dataFieldBox.put(dataField)
    }
}