package com.jorotayo.fl_datatracker.ui.screens.dataEntry

import androidx.compose.runtime.Composable
import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

// =============================================================================
// PREVIEW DATA
// =============================================================================

private val samplePreset = Preset(
    presetId = 1L,
    presetName = "Daily Inspection"
)

private val sampleFields = listOf(
    DataFieldUiState.ShortText(fieldId = 1L, label = "Location", hint = "e.g. Warehouse B"),
    DataFieldUiState.LongText(fieldId = 2L, label = "Notes", hint = "e.g. Some notes"),
    DataFieldUiState.Boolean(fieldId = 3L, label = "Completed"),
    DataFieldUiState.Count(fieldId = 4L, label = "Issues Found", min = 0, max = 20),
    DataFieldUiState.TriState(
        fieldId = 5L,
        label = "Priority",
        options = listOf("Low", "Medium", "High")
    ),
)

private val sampleValues = mapOf(
    1L to "Warehouse B",
    2L to "Everything looks good, minor scuff on the east wall.",
    3L to "true",
    4L to "2",
    5L to "1"
)

// =============================================================================
// PREVIEWS
// =============================================================================

@DefaultPreviews
@Composable
private fun PreviewDataEntryNew() {
    FL_DatatrackerThemeNew {
        DataEntryView(
            state = DataEntryState(
                mode = DataEntryMode.NEW,
                preset = samplePreset,
                fields = sampleFields,
                values = mapOf(1L to "", 2L to "", 3L to "false", 4L to "0", 5L to "-1"),
                recordName = "",
                isReadOnly = false
            )
        )
    }
}

@DefaultPreviews
@Composable
private fun PreviewDataEntryNewFilled() {
    FL_DatatrackerThemeNew {
        DataEntryView(
            state = DataEntryState(
                mode = DataEntryMode.NEW,
                preset = samplePreset,
                fields = sampleFields,
                values = sampleValues,
                recordName = "Morning Round",
                isReadOnly = false
            )
        )
    }
}

@DefaultPreviews
@Composable
private fun PreviewDataEntryReadOnly() {
    FL_DatatrackerThemeNew {
        DataEntryView(
            state = DataEntryState(
                mode = DataEntryMode.EDIT,
                preset = samplePreset,
                fields = sampleFields,
                values = sampleValues,
                recordName = "Morning Round",
                isReadOnly = true,
                editingRecordId = 42L
            )
        )
    }
}

@DefaultPreviews
@Composable
private fun PreviewDataEntryPresetMissing() {
    FL_DatatrackerThemeNew {
        DataEntryView(
            state = DataEntryState(
                mode = DataEntryMode.EDIT,
                preset = null,
                presetMissing = true,
                fields = emptyList(),
                values = mapOf(1L to "Warehouse B", 2L to "Some notes"),
                recordName = "Old Record",
                isReadOnly = true,
                editingRecordId = 7L
            )
        )
    }
}

@DefaultPreviews
@Composable
private fun PreviewDataEntryEditing() {
    FL_DatatrackerThemeNew {
        DataEntryView(
            state = DataEntryState(
                mode = DataEntryMode.EDIT,
                preset = samplePreset,
                fields = sampleFields,
                values = sampleValues,
                recordName = "Morning Round",
                isReadOnly = false,
                editingRecordId = 42L
            )
        )
    }
}

@DefaultPreviews
@Composable
private fun PreviewDataEntryNoDataFieldsInPreset() {
    FL_DatatrackerThemeNew {
        DataEntryView(
            state = DataEntryState(
                mode = DataEntryMode.NEW,
                preset = samplePreset,
                recordName = "",
                isReadOnly = true,
                errors = mapOf(
                    1L to "This field cannot be empty",
                    5L to "Please select an option"
                )
            )
        )
    }
}

@DefaultPreviews
@Composable
private fun PreviewDataEntryWithErrors() {
    FL_DatatrackerThemeNew {
        DataEntryView(
            state = DataEntryState(
                mode = DataEntryMode.NEW,
                preset = samplePreset,
                fields = sampleFields,
                values = mapOf(1L to "", 2L to "", 3L to "false", 4L to "0", 5L to "-1"),
                recordName = "",
                isReadOnly = false,
                errors = mapOf(
                    1L to "This field cannot be empty",
                    5L to "Please select an option"
                )
            )
        )
    }
}

@DefaultPreviews
@Composable
private fun PreviewDataEntrySuccessToast() {
    FL_DatatrackerThemeNew {
        DataEntryView(
            state = DataEntryState(
                mode = DataEntryMode.NEW,
                preset = samplePreset,
                fields = sampleFields,
                values = sampleValues,
                recordName = "Morning Round",
                isReadOnly = false
            )
        )
    }
}