package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.repository.RecordRepository

class DeleteRecordUseCase(
    private val repository: RecordRepository
) {
    operator fun invoke(recordId: Long) {
        repository.deleteRecord(recordId)
    }
}
