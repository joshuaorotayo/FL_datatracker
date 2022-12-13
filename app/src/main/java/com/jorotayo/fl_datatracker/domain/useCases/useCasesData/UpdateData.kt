package com.jorotayo.fl_datatracker.domain.useCases.useCasesData

import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.repository.DataRepository

class UpdateData(
    private val repository: DataRepository,
) {
    operator fun invoke(data: Data): Long {
        return repository.insertData(data)
    }
}