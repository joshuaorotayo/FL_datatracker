package com.jorotayo.fl_datatracker.ui.screens.dataEntry

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData
import com.jorotayo.fl_datatracker.ui.components.toasts.ToastMode
import com.jorotayo.fl_datatracker.ui.scaffold.SetScaffold
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.BooleanField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.CountField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.DateField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.ImageField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.ListField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.LongTextField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.RecordNameField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.ShortTextField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.TimeField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.TriStateField
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMedium
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXXSmall

// =============================================================================
// PREVIEWS
// =============================================================================

private val samplePreset = Preset(presetId = 1L, presetName = "Daily Inspection")

private val sampleFields = listOf(
    DataFieldUiState.ShortText(fieldId = 1L, label = "Location", hint = "Warehouse B"),
    DataFieldUiState.LongText(fieldId = 2L, label = "Notes", hint = "Some notes..."),
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
    2L to "Everything looks good.",
    3L to "true",
    4L to "2",
    5L to "1"
)

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
private fun PreviewDataEntryFilled() {
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
                errors = mapOf(1L to "This field cannot be empty")
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
                isReadOnly = false,
                toast = AppToastData(message = "Record saved successfully.", mode = ToastMode.INFO)
            )
        )
    }
}

// =============================================================================
// ENTRY POINT — stateful, owns ViewModel + scaffold config + toast
// =============================================================================

@Composable
fun DataEntryScreen(
    recordId: Long? = null,
    onNavigateBack: () -> Unit = {},
) {
    val viewModel = hiltViewModel<DataEntryViewModel>()
    val state by viewModel.state.collectAsState()

    // ── Shared scaffold config ────────────────────────────────────────────────
    // navigationIcon wires back navigation into the shared TopAppBar.
    // actions shows the edit button only in read-only EDIT mode.
    // showBottomBar = false — bottom nav hidden on this screen.
    SetScaffold(
        title = {
            Text(
                text = if (state.mode == DataEntryMode.EDIT) "View Entry" else "New Entry",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        actions = {
            if (state.mode == DataEntryMode.EDIT && state.isReadOnly) {
                EditButton(
                    enabled = !state.presetMissing,
                    onClick = { viewModel.onEvent(DataEntryEvent.EnableEditing) }
                )
            }
        },
        showBottomBar = true,
        toast = state.toast
    )

    // ── Load trigger ──────────────────────────────────────────────────────────
    LaunchedEffect(recordId) {
        if (recordId != null) {
            viewModel.onEvent(DataEntryEvent.LoadRecord(recordId))
        } else {
            viewModel.onEvent(DataEntryEvent.LoadFromPreference)
        }
    }

    // ── Content + toast ───────────────────────────────────────────────────────
    Box(modifier = Modifier.fillMaxSize()) {
        DataEntryView(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

// =============================================================================
// DATA ENTRY VIEW — stateless and preview-safe, no ViewModel or Scaffold
// =============================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataEntryView(
    state: DataEntryState,
    onEvent: (DataEntryEvent) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ── Preset-missing warning ─────────────────────────────────────────────
        AnimatedVisibility(
            visible = state.presetMissing,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            PresetMissingBanner()
        }

        // ── Form card ─────────────────────────────────────────────────────────
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Preset name chip — top right
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        Text(
                            text = state.preset?.presetName ?: "Record",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(
                                horizontal = spacingSmall,
                                vertical = spacingXXSmall
                            )
                        )
                    }
                }

                RecordNameField(
                    value = state.recordName,
                    enabled = !state.isReadOnly && !state.presetMissing,
                    onValueChange = { onEvent(DataEntryEvent.UpdateRecordName(it)) }
                )

                Divider(color = MaterialTheme.colorScheme.outlineVariant)

                if (state.presetMissing) {
                    RawDataFallback(values = state.values)
                } else if (state.fields.isEmpty()) {
                    DataFieldsEmptyBanner()
                } else {
                    state.fields.forEach { fieldState ->
                        FormField(
                            fieldState = fieldState,
                            value = state.values[fieldState.fieldId] ?: "",
                            error = state.errors[fieldState.fieldId],
                            isReadOnly = state.isReadOnly,
                            onEvent = onEvent
                        )
                    }
                }
            }
        }

        // ── Save / Clear — hidden in read-only mode ───────────────────────────
        if (!state.isReadOnly && !state.presetMissing && state.fields.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { onEvent(DataEntryEvent.Clear) },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Text("Clear", style = MaterialTheme.typography.labelLarge)
                }

                Button(
                    onClick = { onEvent(DataEntryEvent.Submit) },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save Entry", style = MaterialTheme.typography.labelLarge)
                }
            }
        }

        // ── Navigate To DataFields when Preset fields are empty ───────────────────────────
        if (state.fields.isEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { onEvent(DataEntryEvent.NavigateToDataFields) },
                    modifier = Modifier.weight(0.5f),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardDoubleArrowLeft,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Data Fields", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

// =============================================================================
// EDIT BUTTON
// =============================================================================

@Composable
private fun EditButton(enabled: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = if (enabled) "Edit record" else "Editing disabled — preset deleted",
            tint = if (enabled) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    }
}

// =============================================================================
// PRESET MISSING BANNER
// =============================================================================

@Composable
private fun PresetMissingBanner() {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ErrorOutline,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.size(20.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "Preset deleted",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
                Text(
                    text = "The preset used to create this record no longer exists. " +
                            "Your data is preserved but cannot be edited.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

// =============================================================================
// Empty Data Fields Banner
// =============================================================================

@Composable
private fun DataFieldsEmptyBanner() {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(spacingMedium),
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ErrorOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.size(20.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "No Datafields in Preset",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Text(
                        text = "The currently selected preset has no data fields." +
                                "Please add Data Fields in the Data Fields screen to add a record entry.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
}

// =============================================================================
// RAW DATA FALLBACK
// =============================================================================

@Composable
private fun RawDataFallback(values: Map<Long, String>) {
    if (values.isEmpty()) {
        Text(
            text = "No data was saved with this record.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        return
    }
    Text(
        text = "Saved field data:",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    values.entries.forEachIndexed { index, (_, value) ->
        Text(
            text = "Field ${index + 1}: ${value.ifBlank { "(empty)" }}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

// =============================================================================
// FIELD DISPATCHER
// =============================================================================

@Composable
private fun FormField(
    fieldState: DataFieldUiState,
    value: String,
    error: String?,
    isReadOnly: Boolean,
    onEvent: (DataEntryEvent) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        when (fieldState) {
            is DataFieldUiState.ShortText -> ShortTextField(
                label = fieldState.label,
                value = value,
                onValueChange = {
                    if (!isReadOnly) onEvent(
                        DataEntryEvent.UpdateValue(
                            fieldState.fieldId,
                            it
                        )
                    )
                },
                enabled = !isReadOnly
            )

            is DataFieldUiState.LongText -> LongTextField(
                label = fieldState.label,
                value = value,
                onValueChange = {
                    if (!isReadOnly) onEvent(
                        DataEntryEvent.UpdateValue(
                            fieldState.fieldId,
                            it
                        )
                    )
                },
                enabled = !isReadOnly
            )

            is DataFieldUiState.Boolean -> BooleanField(
                label = fieldState.label,
                value = value.toBooleanStrictOrNull() ?: false,
                onValueChange = {
                    if (!isReadOnly) onEvent(
                        DataEntryEvent.UpdateValue(
                            fieldState.fieldId,
                            it.toString()
                        )
                    )
                },
                enabled = !isReadOnly
            )

            is DataFieldUiState.Date -> DateField(
                label = fieldState.label,
                value = value,
                onValueChange = {
                    if (!isReadOnly) onEvent(
                        DataEntryEvent.UpdateValue(
                            fieldState.fieldId,
                            it
                        )
                    )
                },
                enabled = !isReadOnly
            )

            is DataFieldUiState.Time -> TimeField(
                label = fieldState.label,
                value = value,
                onValueChange = {
                    if (!isReadOnly) onEvent(
                        DataEntryEvent.UpdateValue(
                            fieldState.fieldId,
                            it
                        )
                    )
                },
                enabled = !isReadOnly
            )

            is DataFieldUiState.Count -> CountField(
                label = fieldState.label,
                value = value.toIntOrNull() ?: fieldState.min,
                onValueChange = {
                    if (!isReadOnly) onEvent(
                        DataEntryEvent.UpdateValue(
                            fieldState.fieldId,
                            it.toString()
                        )
                    )
                },
                min = fieldState.min,
                max = fieldState.max,
                enabled = !isReadOnly
            )

            is DataFieldUiState.DynamicList -> ListField(
                label = fieldState.label,
                items = value.split("|").filter { it.isNotBlank() }.ifEmpty { listOf("") },
                onItemsChange = {
                    if (!isReadOnly) onEvent(
                        DataEntryEvent.UpdateValue(
                            fieldState.fieldId,
                            it.joinToString("|")
                        )
                    )
                },
                enabled = !isReadOnly
            )

            is DataFieldUiState.Image -> ImageField(
                label = fieldState.label,
                imageUri = value.toUri(),
                onImageSelected = { uri: Uri? ->
                    if (!isReadOnly) onEvent(
                        DataEntryEvent.UpdateValue(
                            fieldState.fieldId,
                            uri?.toString() ?: ""
                        )
                    )
                },
                enabled = !isReadOnly
            )

            is DataFieldUiState.TriState -> TriStateField(
                label = fieldState.label,
                options = fieldState.options,
                selected = value.toIntOrNull() ?: -1,
                onSelectedChange = {
                    if (!isReadOnly) onEvent(
                        DataEntryEvent.UpdateValue(
                            fieldState.fieldId,
                            it.toString()
                        )
                    )
                },
                enabled = !isReadOnly
            )
        }

        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

private fun String.toUri(): Uri? =
    takeIf { it.isNotBlank() }?.let {
        try {
            Uri.parse(it)
        } catch (_: Exception) {
            null
        }
    }