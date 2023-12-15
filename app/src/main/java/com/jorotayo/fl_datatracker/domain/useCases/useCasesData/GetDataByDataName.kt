package com.jorotayo.fl_datatracker.domain.useCases.useCasesData

import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.repository.DataRepository

class GetDataByDataName(
    private val repository: DataRepository,
) {
    operator fun invoke(dataName: String): Data? {
        return repository.getDataByDataName(dataName)
    }
}
