package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.DataRecord
import com.jorotayo.fl_datatracker.data.repository.RecordRepository
import kotlinx.coroutines.flow.Flow

class GetAllRecordsUseCase(private val repository: RecordRepository) {
    // One-shot (keep existing)
    operator fun invoke(): List<DataRecord> = repository.getAllRecords()

    // Reactive stream
    fun asFlow(): Flow<List<DataRecord>> = repository.observeAllRecords()
}
