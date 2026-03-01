package com.jorotayo.fl_datatracker.ui.screens.dataEntry

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState
import com.jorotayo.fl_datatracker.ui.components.loading.LoadingScreen
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToast
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.BooleanField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.CountField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.DateField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.ImageField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.ListField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.LongTextField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.ShortTextField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.TimeField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.components.TriStateField
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMedium
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXXSmall

// =============================================================================
// ENTRY POINT
// =============================================================================

/**
 * @param recordId  Non-null → edit/view an existing record.
 *                  Null     → create a new record using the preset stored in
 *                             UserPreferenceStore (no argument needed).
 */
@Composable
fun DataEntryScreen(
    recordId: Long? = null,
    onNavigateBack: () -> Unit = {},
    viewModel: DataEntryViewModel = hiltViewModel()
) {
    LaunchedEffect(recordId) {
        if (recordId != null) {
            viewModel.onEvent(DataEntryEvent.LoadRecord(recordId))
        } else {
            viewModel.onEvent(DataEntryEvent.LoadFromPreference)
        }
    }

    val state by viewModel.state.collectAsState()

    if (state.isLoading) {
        LoadingScreen()
        return
    }

    DataEntryContent(
        state = state,
        onNavigateBack = onNavigateBack,
        onEvent = viewModel::onEvent
    )
}

// =============================================================================
// STATELESS CONTENT
// =============================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataEntryContent(
    state: DataEntryState,
    onNavigateBack: () -> Unit = {},
    onEvent: (DataEntryEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = spacingMedium),
                title = {
                    Column {
                        Text(
                            "Data Entry", style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (state.mode == DataEntryMode.EDIT && state.isReadOnly) {
                        EditButton(
                            enabled = !state.presetMissing,
                            onClick = { onEvent(DataEntryEvent.EnableEditing) }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ── Preset-missing warning ─────────────────────────────────────
            AnimatedVisibility(
                visible = state.presetMissing,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                PresetMissingBanner()
            }

            // ── Form fields ────────────────────────────────────────────────
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

                    Divider(color = MaterialTheme.colorScheme.outlineVariant)

                    if (state.presetMissing) {
                        // Preset deleted — show raw saved data, no typed composables
                        RawDataFallback(values = state.values)
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

            // ── Save / Clear — hidden in read-only mode ────────────────────
            if (!state.isReadOnly && !state.presetMissing) {
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
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Save Entry", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }

    AppToast(
        data = state.toast,
        onDismiss = { onEvent(DataEntryEvent.DismissToast) }
    )
}

// =============================================================================
// EDIT BUTTON — greyed out when preset is missing
// =============================================================================

@Composable
private fun EditButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = if (enabled) "Edit record" else "Editing disabled — preset deleted",
            tint = if (enabled)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    }
}

// =============================================================================
// PRESET MISSING BANNER
// =============================================================================

@Composable
private fun PresetMissingBanner() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        ),
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
// RAW DATA FALLBACK (preset deleted — no typed composables available)
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
                    if (!isReadOnly) onEvent(DataEntryEvent.UpdateValue(fieldState.fieldId, it))
                },
                enabled = !isReadOnly
            )

            is DataFieldUiState.LongText -> LongTextField(
                label = fieldState.label,
                value = value,
                onValueChange = {
                    if (!isReadOnly) onEvent(DataEntryEvent.UpdateValue(fieldState.fieldId, it))
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
                    if (!isReadOnly) onEvent(DataEntryEvent.UpdateValue(fieldState.fieldId, it))
                },
                enabled = !isReadOnly
            )

            is DataFieldUiState.Time -> TimeField(
                label = fieldState.label,
                value = value,
                onValueChange = {
                    if (!isReadOnly) onEvent(DataEntryEvent.UpdateValue(fieldState.fieldId, it))
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

        // Inline validation error shown beneath the field
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

// Null-safe URI parse
private fun String.toUri(): Uri? =
    takeIf { it.isNotBlank() }?.let {
        try {
            Uri.parse(it)
        } catch (_: Exception) {
            null
        }
    }