package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.DataRecord
import com.jorotayo.fl_datatracker.data.model.RecordEntry
import com.jorotayo.fl_datatracker.data.repository.RecordRepository
import javax.inject.Inject

data class RecordWithEntries(
    val record: DataRecord,
    val entries: List<RecordEntry>
)

/**
 * Fetches a [DataRecord] and all its [RecordEntry] children.
 * Returns null if no record with that id exists.
 */
class GetRecordWithEntriesUseCase @Inject constructor(
    private val repository: RecordRepository
) {
    operator fun invoke(recordId: Long): RecordWithEntries? {
        val record = repository.getRecordById(recordId) ?: return null
        val entries = repository.getEntriesForRecord(recordId)
        return RecordWithEntries(record, entries)
    }
}