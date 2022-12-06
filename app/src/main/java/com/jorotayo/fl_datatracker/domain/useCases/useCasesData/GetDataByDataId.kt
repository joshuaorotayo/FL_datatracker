package com.jorotayo.fl_datatracker.domain.useCases.useCasesData

import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.repository.DataRepository

class GetDataByDataId(
    private val repository: DataRepository,
) {
    operator fun invoke(dataId: Long): Data {
        return repository.getDataByDataId(dataId)
    }
}