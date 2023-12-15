package com.jorotayo.fl_datatracker.domain.useCases.useCasesData

import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.repository.DataRepository

class DeleteData(
    private val repository: DataRepository,
) {
    operator fun invoke(data: Data) {
        return repository.deleteData(data)
    }
}
