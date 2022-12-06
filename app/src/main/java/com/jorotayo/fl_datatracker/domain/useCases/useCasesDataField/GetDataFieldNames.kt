package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField

import com.jorotayo.fl_datatracker.domain.repository.DataFieldRepository

class GetDataFieldNames(
    private val repository: DataFieldRepository,
) {
    operator fun invoke(): List<String> {
        return repository.getDataFieldNames()
    }
}