package com.jorotayo.fl_datatracker.domain.useCases.useCasesData

import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.repository.DataRepository

class GetData(
    private val repository: DataRepository,
) {
    operator fun invoke(): List<Data> {
        return repository.getData()
    }
}
