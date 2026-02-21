package com.jorotayo.fl_datatracker.data.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.relation.ToOne

@Entity
data class RecordEntry(
    @Id
    var entryId: Long = 0,

    // Link back to the parent record
    @Index
    var recordId: Long = 0,

    // Which DataField definition this answer corresponds to
    @Index
    var dataFieldId: Long = 0,

    // All values stored as strings — DataFieldMapper handles type conversion
    var value: String = ""
) {
    // Back-reference to the parent DataRecord
    lateinit var record: ToOne<DataRecord>
}