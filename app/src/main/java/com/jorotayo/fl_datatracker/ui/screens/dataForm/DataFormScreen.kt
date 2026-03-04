package com.jorotayo.fl_datatracker.ui.screens.dataForm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.components.loading.LoadingOverlay
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToast
import com.jorotayo.fl_datatracker.ui.scaffold.SetScaffold
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.ConfirmDeleteField
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.DeletePreset
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.DismissDeleteDialog
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.DismissToast
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.RequestDeleteField
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.SaveField
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.SavePreset
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.SelectPreset
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.ToggleAddFieldSheet
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent.UpdateField
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.AddDataFieldSheet
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.DataFieldCard
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.DataFieldUi
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.DeleteFieldDialog
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.DeletePresetDialog
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.FieldType
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.FieldUpdate
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.NoDataFieldScreen
import com.jorotayo.fl_datatracker.ui.screens.dataForm.components.PresetSelectorCard
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingHuge
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMedium
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXXSmall

// =============================================================================
// PREVIEW
// =============================================================================

@DefaultPreviews
@Composable
fun PreviewDataFormScreen() {
    FL_DatatrackerThemeNew {
        DataFormScreenView(
            state = DataFormState(
                presets = listOf(Preset(0L, "Default"), Preset(1L, "Custom")),
                selectedPreset = Preset(0L, "Default"),
                fields = listOf(
                    DataFieldUi(
                        id = 1L,
                        name = "Name",
                        type = FieldType.SHORT_TEXT,
                        hint = "Enter name"
                    ),
                    DataFieldUi(id = 2L, name = "Notes", type = FieldType.LONG_TEXT, hint = ""),
                    DataFieldUi(
                        id = 3L,
                        name = "Active",
                        type = FieldType.BOOLEAN,
                        booleanOptions = listOf("Yes", "No")
                    ),
                    DataFieldUi(
                        id = 4L,
                        name = "Priority",
                        type = FieldType.TRISTATE,
                        tristateOptions = listOf("Low", "Med", "High")
                    ),
                    DataFieldUi(id = 5L, name = "Score", type = FieldType.COUNT, hint = "0–100"),
                    DataFieldUi(id = 6L, name = "Photo", type = FieldType.IMAGE, hint = ""),
                    DataFieldUi(id = 7L, name = "Date", type = FieldType.DATE, hint = ""),
                    DataFieldUi(id = 8L, name = "Time", type = FieldType.TIME, hint = ""),
                    DataFieldUi(id = 9L, name = "Tags", type = FieldType.LIST, hint = "")
                )
            )
        )
    }
}

@DefaultPreviews
@Composable
fun PreviewLoadingDataFormScreen() {
    FL_DatatrackerThemeNew {
        DataFormScreenView(
            state = DataFormState(
                isLoading = true,
                presets = listOf(Preset(0L, "Default"), Preset(1L, "Custom")),
                selectedPreset = Preset(0L, "Default"),
                fields = listOf(
                    DataFieldUi(
                        id = 1L,
                        name = "Name",
                        type = FieldType.SHORT_TEXT,
                        hint = "Enter name"
                    ),
                    DataFieldUi(id = 2L, name = "Notes", type = FieldType.LONG_TEXT, hint = ""),
                    DataFieldUi(
                        id = 3L,
                        name = "Active",
                        type = FieldType.BOOLEAN,
                        booleanOptions = listOf("Yes", "No")
                    ),
                    DataFieldUi(
                        id = 4L,
                        name = "Priority",
                        type = FieldType.TRISTATE,
                        tristateOptions = listOf("Low", "Med", "High")
                    ),
                    DataFieldUi(id = 5L, name = "Score", type = FieldType.COUNT, hint = "0–100"),
                    DataFieldUi(id = 6L, name = "Photo", type = FieldType.IMAGE, hint = ""),
                    DataFieldUi(id = 7L, name = "Date", type = FieldType.DATE, hint = ""),
                    DataFieldUi(id = 8L, name = "Time", type = FieldType.TIME, hint = ""),
                    DataFieldUi(id = 9L, name = "Tags", type = FieldType.LIST, hint = "")
                )
            )
        )
    }
}

@Composable
fun DataFormScreen() {
    val viewModel = hiltViewModel<DataFormViewModel>()
    val state by viewModel.state.collectAsState()

    SetScaffold(
        title = {
            Text(
                text = "Data Forms",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        fab = {
            if (state.fields.isNotEmpty()) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.onEvent(ToggleAddFieldSheet) },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text("Add Field", style = MaterialTheme.typography.labelLarge) }
                )
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            LoadingOverlay()
        } else {
            DataFormScreenView(
                state = state,
                onEvent = viewModel::onEvent
            )
            AppToast(
                data = state.toast,
                onDismiss = { viewModel.onEvent(DismissToast) }
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataFormScreenView(
    state: DataFormState,
    onEvent: (DataFormEvent) -> Unit = {}
) {
    var selectedPresetName by remember(state.selectedPreset?.presetId) {
        mutableStateOf(state.selectedPreset?.presetName ?: "Default")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp)
    ) {
        // Preset selector always visible
        PresetSelectorCard(
            modifier = Modifier.padding(spacingMedium),
            selectedPreset = selectedPresetName,
            presets = state.presets,
            onPresetSelected = { preset ->
                selectedPresetName = preset.presetName
                onEvent(SelectPreset(preset))
            },
            onDeletePreset = { preset ->
                onEvent(DeletePreset(preset))
            },
            onCreatePreset = { name ->
                onEvent(SavePreset(name))
            }
        )

        if (state.fields.isEmpty()) {
            NoDataFieldScreen(
                onAddFieldClick = { onEvent(ToggleAddFieldSheet) }
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(spacingMedium),
                verticalArrangement = Arrangement.spacedBy(spacingSmall)
            ) {
                // Section Header
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = spacingXSmall),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Fields",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ) {
                            Text(
                                text = "${state.fields.size} fields",
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(
                                    horizontal = spacingSmall,
                                    vertical = spacingXXSmall
                                )
                            )
                        }
                    }
                }

                // Data Fields List
                items(state.fields, key = { it.id }) { field ->
                    DataFieldCard(
                        field = field,
                        onDelete = { onEvent(RequestDeleteField(field)) },
                        onToggleActive = {
                            onEvent(
                                UpdateField(
                                    field,
                                    FieldUpdate.ToggleActive
                                )
                            )
                        },
                        onHintUpdate = { newHint ->
                            onEvent(
                                UpdateField(
                                    field,
                                    FieldUpdate.Hint(newHint)
                                )
                            )
                        },
                        onBooleanOptionsUpdate = { options ->
                            onEvent(
                                UpdateField(
                                    field,
                                    FieldUpdate.BooleanOptions(options)
                                )
                            )
                        },
                        onTristateOptionsUpdate = { options ->
                            onEvent(
                                UpdateField(
                                    field,
                                    FieldUpdate.TristateOptions(options)
                                )
                            )
                        },
                        onTypeChange = { newType ->
                            onEvent(
                                UpdateField(
                                    field,
                                    FieldUpdate.Type(newType)
                                )
                            )
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(spacingHuge)) }
            }
        }
    }

    if (state.showDeleteFieldDialog && state.fieldToDelete != null) {
        DeleteFieldDialog(
            fieldName = state.fieldToDelete.name,
            onConfirm = { onEvent(ConfirmDeleteField) },
            onDismiss = { onEvent(DismissDeleteDialog) }
        )
    }

    if (state.showDeletePresetDialog && state.presetToDelete != null) {
        DeletePresetDialog(
            onEvent = onEvent,
            state = state
        )
    }

    if (state.showAddFieldSheet) {
        AddDataFieldSheet(
            onDismiss = { onEvent(ToggleAddFieldSheet) },
            onSave = { newField ->
                onEvent(SaveField(newField))
            }
        )
    }
}
