package com.jorotayo.fl_datatracker.domain.util

import com.jorotayo.fl_datatracker.data.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState


/**
 * Converts a [DataField] entity into its corresponding [DataFieldUiState].
 *
 * Storage conventions used by [DataField]:
 * - [DataFieldType.BOOLEAN]  — first = trueLabel override, second = falseLabel override
 * - [DataFieldType.TRISTATE] — first / second / third = option labels
 * - [DataFieldType.COUNT]    — first = min (Int), second = max (Int)
 * - [DataFieldType.LIST]     — first = pipe-delimited item string e.g. "Apples|Bananas"
 * - [DataFieldType.IMAGE]    — first = URI string (empty = no image)
 * - All others               — first/second/third unused for config; fieldHint used as hint
 */
fun DataField.toUiState(): DataFieldUiState {
    val hint = fieldHint ?: "Enter value for $fieldName"
    return when (dataFieldType) {
        DataFieldType.SHORT_TEXT -> DataFieldUiState.ShortText(
            fieldId = dataFieldId,
            label = fieldName,
            hint = hint
        )

        DataFieldType.LONG_TEXT -> DataFieldUiState.LongText(
            fieldId = dataFieldId,
            label = fieldName,
            hint = hint
        )

        DataFieldType.BOOLEAN -> DataFieldUiState.Boolean(
            fieldId = dataFieldId,
            label = fieldName,
            trueLabel = first.ifBlank { "Yes" },
            falseLabel = second.ifBlank { "No" }
        )

        DataFieldType.DATE -> DataFieldUiState.Date(
            fieldId = dataFieldId,
            label = fieldName
        )

        DataFieldType.TIME -> DataFieldUiState.Time(
            fieldId = dataFieldId,
            label = fieldName
        )

        DataFieldType.COUNT -> DataFieldUiState.Count(
            fieldId = dataFieldId,
            label = fieldName,
            min = first.toIntOrNull() ?: 0,
            max = second.toIntOrNull() ?: 999_999
        )

        DataFieldType.TRISTATE -> DataFieldUiState.TriState(
            fieldId = dataFieldId,
            label = fieldName,
            options = listOf(
                first.ifBlank { "Low" },
                second.ifBlank { "Medium" },
                third.ifBlank { "High" }
            )
        )

        DataFieldType.IMAGE -> DataFieldUiState.Image(
            fieldId = dataFieldId,
            label = fieldName,
            uri = first.toUri()
        )

        DataFieldType.LIST -> DataFieldUiState.DynamicList(
            fieldId = dataFieldId,
            label = fieldName,
            items = first.toListItems()
        )
    }
}

// =============================================================================
// BACK-MAPPER — DataFieldUiState → DataField (persist values back to entity)
// =============================================================================

/**
 * Merges the current UI state values back into the original [DataField] entity,
 * ready to be saved to ObjectBox.
 *
 * The [source] entity is copied so the original is never mutated directly.
 */
fun DataField.applyUiState(state: DataFieldUiState): DataField = when (state) {
    is DataFieldUiState.ShortText -> copy(first = state.value)

    is DataFieldUiState.LongText -> copy(first = state.value)

    is DataFieldUiState.Boolean -> copy(
        first = if (state.value) "true" else "false",
        second = state.trueLabel,
        third = state.falseLabel
    )

    is DataFieldUiState.Date -> copy(first = state.value)

    is DataFieldUiState.Time -> copy(first = state.value)

    is DataFieldUiState.Count -> copy(
        first = state.min.toString(),
        second = state.max.toString(),
        third = state.value.toString()
    )

    is DataFieldUiState.TriState -> copy(
        first = state.options.getOrElse(0) { "Low" },
        second = state.options.getOrElse(1) { "Medium" },
        third = state.options.getOrElse(2) { "High" }
        // selected index would be stored separately in a DataEntry, not the field definition
    )

    is DataFieldUiState.Image -> copy(first = state.uri?.toString() ?: "")

    is DataFieldUiState.DynamicList -> copy(first = state.items.toDelimited())
}









