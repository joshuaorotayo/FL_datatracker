package com.jorotayo.fl_datatracker.data.repository

import com.jorotayo.fl_datatracker.data.model.DataRecord
import com.jorotayo.fl_datatracker.data.model.RecordEntry
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    fun getAllRecords(): List<DataRecord>
    fun observeAllRecords(): Flow<List<DataRecord>>
    fun getRecordsForPreset(presetId: Long): List<DataRecord>
    fun getEntriesForRecord(recordId: Long): List<RecordEntry>
    fun saveRecord(
        record: DataRecord,
        entries: List<RecordEntry>,
        existingRecordId: Long? = null
    ): Result<Long>

    fun deleteRecord(recordId: Long)
}
