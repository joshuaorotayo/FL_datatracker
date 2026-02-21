package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.RecordEntry
import com.jorotayo.fl_datatracker.data.repository.RecordRepository

class GetRecordEntriesUseCase(
    private val repository: RecordRepository
) {
    operator fun invoke(recordId: Long): List<RecordEntry> =
        repository.getEntriesForRecord(recordId)
}