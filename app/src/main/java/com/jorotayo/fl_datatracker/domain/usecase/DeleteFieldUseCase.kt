package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.repository.DataFieldRepository

class DeleteFieldUseCase(
    private val repository: DataFieldRepository
) {
    operator fun invoke(fieldId: Long) {
        repository.deleteField(fieldId)
    }
}
