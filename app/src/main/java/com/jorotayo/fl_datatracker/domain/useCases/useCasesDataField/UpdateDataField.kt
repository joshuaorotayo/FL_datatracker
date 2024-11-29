package com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField

import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.repository.DataFieldRepository

class UpdateDataField(
    private val repository: DataFieldRepository,
) {
    operator fun invoke(dataField: DataField) {
        return repository.updateDataField(dataField)
    }
}
