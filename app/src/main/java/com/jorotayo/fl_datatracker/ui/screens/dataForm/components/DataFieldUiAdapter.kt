package com.jorotayo.fl_datatracker.ui.screens.dataForm.components

import com.jorotayo.fl_datatracker.data.model.DataField
import com.jorotayo.fl_datatracker.domain.util.DataFieldType

/**
 * ADAPTER MAPPER
 *
 * You already have a mapper that converts:
 * DataField (domain/ObjectBox) ↔ DataFieldUiState (sealed class for entry forms)
 *
 * But your DataFormScreen uses a simpler model:
 * DataFieldUi (for the field MANAGEMENT screen, not data entry)
 *
 * This adapter bridges the gap between them.
 */

// =============================================================================
// DataField (ObjectBox entity) → DataFieldUi (management screen model)
// =============================================================================

/**
 * Convert ObjectBox DataField entity to the simpler DataFieldUi model
 * used in the field management screen
 */
fun DataField.toDataFieldUi(): DataFieldUi {
    return DataFieldUi(
        id = this.dataFieldId,
        name = this.fieldName,
        type = this.dataFieldType.toUiFieldType(),
        hint = this.fieldHint ?: "",
        booleanOptions = when (this.dataFieldType) {
            DataFieldType.BOOLEAN -> listOf(
                this.first.ifBlank { "Yes" },
                this.second.ifBlank { "No" }
            )

            else -> emptyList()
        },
        tristateOptions = when (this.dataFieldType) {
            DataFieldType.TRISTATE -> listOf(
                this.first.ifBlank { "Low" },
                this.second.ifBlank { "Medium" },
                this.third.ifBlank { "High" }
            )

            else -> emptyList()
        },
        isActive = this.isActive
    )
}

// =============================================================================
// DataFieldUi (management screen model) → DataField (ObjectBox entity)
// =============================================================================

/**
 * Convert DataFieldUi back to ObjectBox DataField entity
 * for saving via use cases
 */
fun DataFieldUi.toDataField(presetId: Long = 0L): DataField {
    return DataField(presetId = presetId).apply {
        this.dataFieldId = this@toDataField.id
        this.fieldName = this@toDataField.name
        this.dataFieldType = this@toDataField.type.toDomainFieldType()
        this.fieldHint = this@toDataField.hint
        this.isActive = this@toDataField.isActive
        this.presetId = presetId

        // Store boolean options
        when (this@toDataField.type) {
            FieldType.BOOLEAN -> {
                if (this@toDataField.booleanOptions.size >= 2) {
                    this.first = this@toDataField.booleanOptions[0]
                    this.second = this@toDataField.booleanOptions[1]
                }
            }

            FieldType.TRISTATE -> {
                if (this@toDataField.tristateOptions.size >= 3) {
                    this.first = this@toDataField.tristateOptions[0]
                    this.second = this@toDataField.tristateOptions[1]
                    this.third = this@toDataField.tristateOptions[2]
                }
            }

            else -> {
                // For other types, first/second/third are unused for config
            }
        }
    }
}

// =============================================================================
// Type Conversions
// =============================================================================

/**
 * Convert domain DataFieldType to UI FieldType
 */
private fun DataFieldType.toUiFieldType(): FieldType {
    return when (this) {
        DataFieldType.SHORT_TEXT -> FieldType.SHORT_TEXT
        DataFieldType.LONG_TEXT -> FieldType.LONG_TEXT
        DataFieldType.BOOLEAN -> FieldType.BOOLEAN
        DataFieldType.DATE -> FieldType.DATE
        DataFieldType.TIME -> FieldType.TIME
        DataFieldType.COUNT -> FieldType.COUNT
        DataFieldType.TRISTATE -> FieldType.TRISTATE
        DataFieldType.IMAGE -> FieldType.IMAGE
        DataFieldType.LIST -> FieldType.LIST
    }
}

/**
 * Convert UI FieldType to domain DataFieldType
 */
private fun FieldType.toDomainFieldType(): DataFieldType {
    return when (this) {
        FieldType.SHORT_TEXT -> DataFieldType.SHORT_TEXT
        FieldType.LONG_TEXT -> DataFieldType.LONG_TEXT
        FieldType.BOOLEAN -> DataFieldType.BOOLEAN
        FieldType.DATE -> DataFieldType.DATE
        FieldType.TIME -> DataFieldType.TIME
        FieldType.COUNT -> DataFieldType.COUNT
        FieldType.TRISTATE -> DataFieldType.TRISTATE
        FieldType.IMAGE -> DataFieldType.IMAGE
        FieldType.LIST -> DataFieldType.LIST
    }
}

/**
 * USAGE IN VIEWMODEL:
 *
 * Replace the old mapper imports with these:
 *
 * import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.toDataFieldUi
 * import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.toDataField
 *
 * Then use like this:
 *
 * // Loading (DataField → DataFieldUi)
 * private fun loadFieldsForPreset(presetId: Long) {
 *     viewModelScope.launch {
 *         val domainFields = getFields(presetId)  // Returns List<DataField>
 *         val uiFields = domainFields.map { it.toDataFieldUi() }  // Convert
 *         _state.update { it.copy(fields = uiFields) }
 *     }
 * }
 *
 * // Saving (DataFieldUi → DataField)
 * private fun onUpdateField(event: DataFormEvent.UpdateField) {
 *     val updated = applyUpdate(event.field, event.update)
 *     viewModelScope.launch {
 *         val presetId = _state.value.selectedPreset?.presetId ?: 0L
 *         saveField(updated.toDataField(presetId))  // Convert
 *     }
 * }
 */

/**
 * ARCHITECTURE EXPLANATION:
 *
 * You now have TWO different UI models for DataField:
 *
 * 1. DataFieldUiState (sealed class)
 *    - Used in DATA ENTRY screens
 *    - Different subtype for each field type
 *    - Holds actual entered values
 *    - Example: DataFieldUiState.ShortText(value = "John Doe")
 *
 * 2. DataFieldUi (simple data class)
 *    - Used in FIELD MANAGEMENT screen
 *    - Same structure for all field types
 *    - Holds field configuration, not entered data
 *    - Example: DataFieldUi(name = "Name", type = SHORT_TEXT, hint = "Enter name")
 *
 * Your existing mapper (toUiState/applyUiState) is for #1
 * This new mapper (toDataFieldUi/toDataField) is for #2
 *
 * Both are valid and serve different purposes!
 */
