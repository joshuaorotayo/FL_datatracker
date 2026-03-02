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
    operator fun invoke(
        presetId: Long,
        fields: List<DataFieldUiState>,
        values: Map<Long, String>,
        recordName: String,
        recordId: Long? = null
    ): Result<Serializable> {

        // Validate field values only — record name is optional
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

        val record = DataRecord(
            recordId = recordId ?: 0L,
            presetId = presetId,
            // Empty name falls back to "Untitled Record" — matches HomeScreen display
            title = recordName.trim().ifBlank { "Untitled Record" }
        )
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