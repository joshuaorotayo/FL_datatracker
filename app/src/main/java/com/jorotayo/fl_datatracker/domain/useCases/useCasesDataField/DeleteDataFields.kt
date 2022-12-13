package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField

import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.repository.DataFieldRepository

class DeleteDataFields(
    private val repository: DataFieldRepository,
) {
    operator fun invoke(dataFields: List<DataField>) {
        return repository.deleteDataFields(dataFields)
    }
}