package com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen.components.DataFieldCard
import com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen.components.DataFieldUi
import com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen.components.FieldType
import com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen.components.NoDataFieldScreen
import com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen.components.PresetSelectorCard
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMassive
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMedium
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXXSmall

@DefaultPreviews
@Composable
fun PreviewImprovedDataFieldsScreen() {
    FL_DatatrackerThemeNew {
        ImprovedDataFieldsScreen(
            state = DataFieldsState(
                presets = listOf(Preset(0L, "Default"), Preset(1L, "Custom")),
                selectedPreset = Preset(0L, "Default"),
                fields = listOf(
                    DataFieldUi(
                        id = 1L,
                        name = "String",
                        type = FieldType.SHORT_TEXT,
                        hint = "Enter String for short text",
                    ),
                    DataFieldUi(
                        id = 2L,
                        name = "Long Text",
                        type = FieldType.LONG_TEXT,
                        hint = "Enter String for Long text",
                    ),
                    DataFieldUi(
                        id = 3L,
                        name = "Boolean",
                        type = FieldType.BOOLEAN,
                        hint = "Select boolean option",
                        booleanOptions = listOf("Yes", "No")
                    ),
                    DataFieldUi(
                        id = 4L,
                        name = "Tristate",
                        type = FieldType.TRISTATE,
                        hint = "Select option for triple text",
                        tristateOptions = listOf("Yes", "Maybe", "No")
                    ),
                    DataFieldUi(
                        id = 5L,
                        name = "Count",
                        type = FieldType.COUNT,
                        hint = "Enter Count",
                    ),
                    DataFieldUi(
                        id = 6L,
                        name = "Image",
                        type = FieldType.IMAGE,
                        hint = "Select Image",
                    ),
                    DataFieldUi(
                        id = 7L,
                        name = "Date",
                        type = FieldType.DATE,
                        hint = "Enter date",
                    ),
                    DataFieldUi(
                        id = 8L,
                        name = "Time",
                        type = FieldType.TIME,
                        hint = "Enter time",
                    ),
                    DataFieldUi(
                        id = 9L,
                        name = "List",
                        type = FieldType.LIST,
                        hint = "Enter List of items",
                    )
                )
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImprovedDataFieldsScreen(
    state: DataFieldsState,
    onEvent: (DataFieldsEvent) -> Unit = {}
) {
    var selectedPresetName by remember {
        mutableStateOf(state.selectedPreset?.presetName ?: "Default")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Data Fields", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            if (state.fields.isNotEmpty()) {
                ExtendedFloatingActionButton(
                    onClick = { onEvent(DataFieldsEvent.AddField) },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text("Add Field", style = MaterialTheme.typography.labelLarge) }
                )
            }
        }
    ) { paddingValues ->
        if (state.fields.isEmpty()) {
            NoDataFieldScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(spacingMedium),
                verticalArrangement = Arrangement.spacedBy(spacingSmall)
            ) {
                item {
                    PresetSelectorCard(
                        selectedPreset = selectedPresetName,
                        presets = state.presets,
                        onPresetSelected = { preset ->
                            selectedPresetName = preset.presetName
                            onEvent(DataFieldsEvent.SelectPreset(preset))
                        }
                    )
                }

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

                items(state.fields) { field ->
                    DataFieldCard(
                        field = field,
                        onDelete = { onEvent(DataFieldsEvent.DeleteField(field)) },
                        onToggleActive = { onEvent(DataFieldsEvent.ToggleFieldActive(field)) },
                        onHintUpdate = { hint -> onEvent(DataFieldsEvent.UpdateHint(field, hint)) },
                        onBooleanOptionsUpdate = { opts ->
                            onEvent(
                                DataFieldsEvent.UpdateBooleanOptions(
                                    field,
                                    opts
                                )
                            )
                        },
                        onTristateOptionsUpdate = { opts ->
                            onEvent(
                                DataFieldsEvent.UpdateTristateOptions(
                                    field,
                                    opts
                                )
                            )
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(spacingMassive)) }
            }
        }
    }
}