package com.jorotayo.fl_datatracker.domain.useCases.useCasesData

import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.repository.DataRepository

class AddData(
    private val repository: DataRepository,
) {
    suspend operator fun invoke(data: Data) {
        return repository.insertData(data)
    }
}