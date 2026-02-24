package com.jorotayo.fl_datatracker.data.objectbox

import com.jorotayo.fl_datatracker.data.model.DataRecord
import com.jorotayo.fl_datatracker.data.model.DataRecord_
import com.jorotayo.fl_datatracker.data.model.RecordEntry
import com.jorotayo.fl_datatracker.data.model.RecordEntry_
import com.jorotayo.fl_datatracker.data.repository.RecordRepository
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObjectBoxRecordRepository @Inject constructor(
    store: BoxStore
) : RecordRepository {

    private val recordBox = store.boxFor<DataRecord>()
    private val entryBox = store.boxFor<RecordEntry>()

    override fun getRecordsForPreset(presetId: Long): List<DataRecord> =
        recordBox.query {
            equal(DataRecord_.presetId, presetId)
            orderDesc(DataRecord_.createdAt) // newest first for Home screen
        }.find()

    override fun getEntriesForRecord(recordId: Long): List<RecordEntry> =
        entryBox.query {
            equal(RecordEntry_.recordId, recordId)
        }.find()

    /**
     * Uniqueness is enforced here before hitting ObjectBox.
     *
     * A record is considered a duplicate if the same preset already has a record
     * created within the same second. In practice two submissions in the same
     * second is impossible through normal use, but we guard it explicitly.
     *
     * For edit flows, pass the existing [recordId] so the check ignores itself.
     */
    override fun saveRecord(
        record: DataRecord,
        entries: List<RecordEntry>,
        existingRecordId: Long?
    ): Result<Long> {
        val oneSecondWindow = 1_000L
        val isDuplicate = recordBox.query {
            equal(DataRecord_.presetId, record.presetId)
            between(
                DataRecord_.createdAt,
                record.createdAt - oneSecondWindow,
                record.createdAt + oneSecondWindow
            )
        }.find().any { it.recordId != (existingRecordId ?: -1L) }

        if (isDuplicate) {
            return Result.failure(
                IllegalStateException("A record for this preset was already saved moments ago.")
            )
        }

        val recordId = recordBox.put(record)
        entries.forEach { entry ->
            entryBox.put(entry.copy(recordId = recordId))
        }
        return Result.success(recordId)
    }

    override fun deleteRecord(recordId: Long) {
        // Delete all child entries first, then the parent record
        entryBox.query {
            equal(RecordEntry_.recordId, recordId)
        }.find().forEach { entryBox.remove(it.entryId) }

        recordBox.remove(recordId)
    }

    override fun observeAllRecords(): Flow<List<DataRecord>> = callbackFlow {
        val query = recordBox.query().build()

        val subscription = query.subscribe()
            .observer { data -> trySend(data) }

        awaitClose {
            subscription.cancel()
            query.close()
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllRecords(): List<DataRecord> = recordBox.all
}
