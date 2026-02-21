package com.jorotayo.fl_datatracker.data.objectbox

import com.jorotayo.fl_datatracker.data.model.DataField
import com.jorotayo.fl_datatracker.data.model.DataField_
import com.jorotayo.fl_datatracker.data.repository.DataFieldRepository
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObjectBoxDataFieldRepository @Inject constructor(
    store: BoxStore
) : DataFieldRepository {

    private val box: Box<DataField> = store.boxFor(DataField::class.java)

    override fun getFieldsForPreset(presetId: Long): List<DataField> =
        box.query(DataField_.presetId.equal(presetId)).build().find()

    override fun saveField(field: DataField): Long =
        box.put(field)

    override fun deleteField(fieldId: Long) =
        box.remove(fieldId)
}