package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.DataField
import com.jorotayo.fl_datatracker.data.repository.DataFieldRepository

class SaveFieldUseCase(
    private val repository: DataFieldRepository
) {
    operator fun invoke(field: DataField): Result<Long> {
        if (field.fieldName.isBlank()) {
            return Result.failure(IllegalArgumentException("Field name cannot be blank."))
        }

        if (repository.fieldNameExistsInPreset(
                name = field.fieldName,
                presetId = field.presetId,
                excludeId = field.dataFieldId
            )
        ) {
            return Result.failure(
                IllegalArgumentException("A field named \"${field.fieldName}\" already exists in this preset.")
            )
        }

        return Result.success(
            repository.saveField(field.copy(fieldName = field.fieldName.trim()))
        )
    }
}
