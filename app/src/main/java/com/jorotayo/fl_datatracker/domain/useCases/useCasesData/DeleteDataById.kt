package com.jorotayo.fl_datatracker.domain.useCases.useCasesData

import com.jorotayo.fl_datatracker.domain.repository.DataRepository

class DeleteDataById(
    private val repository: DataRepository,
) {
    operator fun invoke(dataId: Long) {
        return repository.deleteDataById(dataId)
    }
}
