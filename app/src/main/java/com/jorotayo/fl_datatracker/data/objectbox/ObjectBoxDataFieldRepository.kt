package com.jorotayo.fl_datatracker.data.objectbox

import com.jorotayo.fl_datatracker.data.model.DataField
import com.jorotayo.fl_datatracker.data.model.DataField_
import com.jorotayo.fl_datatracker.data.repository.DataFieldRepository
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.query.QueryBuilder.StringOrder
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

    override fun fieldNameExistsInPreset(
        name: String,
        presetId: Long,
        excludeId: Long
    ): Boolean {
        val query = box.query(
            DataField_.presetId.equal(presetId)
                .and(DataField_.fieldName.equal(name.trim(), StringOrder.CASE_INSENSITIVE))
                .and(DataField_.dataFieldId.notEqual(excludeId))
        ).build()

        return query.use { it.count() > 0 }
    }
}
