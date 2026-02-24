package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.DataField
import com.jorotayo.fl_datatracker.data.repository.DataFieldRepository

class SaveFieldUseCase(
    private val repository: DataFieldRepository
) {
    /**
     * Validates the field has a non-blank name and a unique name within the preset,
     * then saves. Returns the field id on success.
     */
    operator fun invoke(field: DataField, existingFields: List<DataField>): Result<Long> {
        if (field.fieldName.isBlank()) {
            return Result.failure(IllegalArgumentException("Field name cannot be blank."))
        }
        val isDuplicate = existingFields
            .filter { it.dataFieldId != field.dataFieldId }
            .any { it.fieldName.equals(field.fieldName.trim(), ignoreCase = true) }

        if (isDuplicate) {
            return Result.failure(
                IllegalArgumentException("A field named \"${field.fieldName}\" already exists in this preset.")
            )
        }
        val id = repository.saveField(field.copy(fieldName = field.fieldName.trim()))
        return Result.success(id)
    }
}
