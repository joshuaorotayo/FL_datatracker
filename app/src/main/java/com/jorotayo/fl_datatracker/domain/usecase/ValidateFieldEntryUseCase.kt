package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState


class ValidateFieldEntryUseCase {
    /**
     * Validates a raw string value against the rules for the given field type.
     * Returns Result.success(Unit) if valid, Result.failure with a descriptive
     * message if not.
     */
    operator fun invoke(field: DataFieldUiState, value: String): Result<Unit> {
        return when (field) {
            is DataFieldUiState.ShortText -> validateNotBlank(value, field.label)
                ?: validateMaxLength(value, 50, field.label)
                ?: Result.success(Unit)

            is DataFieldUiState.LongText -> validateNotBlank(value, field.label)
                ?: validateMaxLength(value, 200, field.label)
                ?: Result.success(Unit)

            // Boolean always has a value (true/false default), never invalid
            is DataFieldUiState.Boolean -> Result.success(Unit)

            is DataFieldUiState.Date -> if (value.isBlank())
                Result.failure(IllegalArgumentException("${field.label}: please select a date."))
            else Result.success(Unit)

            is DataFieldUiState.Time -> if (value.isBlank())
                Result.failure(IllegalArgumentException("${field.label}: please select a time."))
            else Result.success(Unit)

            is DataFieldUiState.Count -> {
                val parsed = value.replace(",", "").toIntOrNull()
                when {
                    parsed == null ->
                        Result.failure(IllegalArgumentException("${field.label}: must be a number."))

                    parsed < field.min ->
                        Result.failure(IllegalArgumentException("${field.label}: minimum value is ${field.min}."))

                    parsed > field.max ->
                        Result.failure(IllegalArgumentException("${field.label}: maximum value is ${field.max}."))

                    else -> Result.success(Unit)
                }
            }

            is DataFieldUiState.TriState -> {
                val selected = value.toIntOrNull() ?: -1
                if (selected < 0)
                    Result.failure(IllegalArgumentException("${field.label}: please select an option."))
                else Result.success(Unit)
            }

            is DataFieldUiState.Image -> if (value.isBlank())
                Result.failure(IllegalArgumentException("${field.label}: please select an image."))
            else Result.success(Unit)

            is DataFieldUiState.DynamicList -> {
                val items = value.split("|").filter { it.isNotBlank() }
                if (items.isEmpty())
                    Result.failure(IllegalArgumentException("${field.label}: please add at least one item."))
                else Result.success(Unit)
            }
        }
    }

    private fun validateNotBlank(value: String, label: String): Result<Unit>? =
        if (value.isBlank())
            Result.failure(IllegalArgumentException("$label cannot be blank."))
        else null

    private fun validateMaxLength(value: String, max: Int, label: String): Result<Unit>? =
        if (value.length > max)
            Result.failure(IllegalArgumentException("$label cannot exceed $max characters."))
        else null
}
