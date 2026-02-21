package com.jorotayo.fl_datatracker.data.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index

@Entity
data class DataRecord(
    @Id
    var recordId: Long = 0,

    // Which preset this record belongs to
    @Index
    var presetId: Long = 0,

    // Human-readable title — defaults to preset name + date, can be overridden
    var title: String = "",

    // Millisecond timestamps
    var createdAt: Long = System.currentTimeMillis(),
    var updatedAt: Long = System.currentTimeMillis()
) {
    // ObjectBox requires a no-arg constructor for relations — provided by default
    // values above. ToMany is populated lazily by ObjectBox.
//    @JvmField
//    val entries: ToMany<RecordEntry> = ToMany(this, DataRecord)
}