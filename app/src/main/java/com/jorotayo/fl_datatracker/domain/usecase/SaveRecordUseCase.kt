package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.DataRecord
import com.jorotayo.fl_datatracker.data.model.RecordEntry
import com.jorotayo.fl_datatracker.data.repository.RecordRepository
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState
import java.io.Serializable

class SaveRecordUseCase(
    private val recordRepository: RecordRepository,
    private val validateEntry: ValidateFieldEntryUseCase
) {
    /**
     * Validates every field value before saving.
     * Returns a map of fieldId → error message if validation fails,
     * or the saved record id on success.
     */
    operator fun invoke(
        presetId: Long,
        fields: List<DataFieldUiState>,
        values: Map<Long, String>
    ): Result<Serializable> {
        // Validate all fields and collect any errors
        val errors = fields
            .mapNotNull { field ->
                val value = values[field.fieldId] ?: ""
                val result = validateEntry(field, value)
                if (result.isFailure) {
                    field.fieldId to (result.exceptionOrNull()?.message ?: "Invalid value")
                } else null
            }
            .toMap()

        if (errors.isNotEmpty()) {
            return Result.failure(ValidationException(errors))
        }

        val record = DataRecord(presetId = presetId)
        val entries = fields.map { field ->
            RecordEntry(
                dataFieldId = field.fieldId,
                value = values[field.fieldId] ?: ""
            )
        }
        val id = recordRepository.saveRecord(record, entries)
        return Result.success(id)
    }
}

class ValidationException(
    val errors: Map<Long, String>
) : Exception("Validation failed for ${errors.size} field(s).")
