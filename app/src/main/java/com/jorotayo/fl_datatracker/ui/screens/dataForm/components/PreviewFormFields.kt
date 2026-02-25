package com.jorotayo.fl_datatracker.ui.screens.dataForm.components

import android.net.Uri
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.BooleanField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.CountField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.DateField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.ImageField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.ListField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.LongTextField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.ShortTextField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.TimeField
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.TriStateField
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

private val samplePresets = listOf("Default", "Medical", "Fitness", "Finance")

@DefaultPreviews
@Composable
fun PreviewFormFieldComponents() {
    FL_DatatrackerThemeNew {
        FormFieldComponentsPreview()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormFieldComponentsPreview(
    onNavigateBack: () -> Unit = {},
    onSave: () -> Unit = {}
) {
    // Field state
    var shortText by remember { mutableStateOf("") }
    var longText by remember { mutableStateOf("") }
    var booleanValue by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var countValue by remember { mutableIntStateOf(0) }
    var listItems by remember { mutableStateOf(listOf("")) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var triState by remember { mutableIntStateOf(-1) }

    // Preset dropdown state
    var selectedPreset by remember { mutableStateOf(samplePresets.first()) }
    var presetDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Data Fields",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* add new field */ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add data field")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ── Preset selector ────────────────────────────────────────────────
            Column(
                modifier = Modifier.padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Preset",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                ExposedDropdownMenuBox(
                    expanded = presetDropdownExpanded,
                    onExpandedChange = { presetDropdownExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedPreset,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = presetDropdownExpanded)
                        },
                        shape = MaterialTheme.shapes.medium,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    ExposedDropdownMenu(
                        expanded = presetDropdownExpanded,
                        onDismissRequest = { presetDropdownExpanded = false }
                    ) {
                        samplePresets.forEach { preset ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = preset,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                onClick = {
                                    selectedPreset = preset
                                    presetDropdownExpanded = false
                                },
                                trailingIcon = if (preset == selectedPreset) {
                                    {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                } else {
                                    null
                                }
                            )
                        }
                    }
                }
            }

            // ── Form card ──────────────────────────────────────────────────────
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
                    Text(
                        text = selectedPreset,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Divider(color = MaterialTheme.colorScheme.outlineVariant)

                    ShortTextField(
                        label = "Short",
                        value = shortText,
                        onValueChange = { shortText = it }
                    )

                    LongTextField(
                        label = "Long Data",
                        value = longText,
                        onValueChange = { longText = it }
                    )

                    BooleanField(
                        label = "Two Options",
                        value = booleanValue,
                        onValueChange = { booleanValue = it }
                    )

                    DateField(
                        label = "Date",
                        value = selectedDate,
                        onPickDate = {}
                    )

                    TimeField(
                        label = "Time",
                        value = selectedTime,
                        onPickTime = {}
                    )

                    CountField(
                        label = "Count",
                        value = countValue,
                        onValueChange = { countValue = it },
                        min = 0,
                        max = 99
                    )

                    ListField(
                        label = "Dynamic List",
                        items = listItems,
                        onItemsChange = { listItems = it }
                    )

                    ImageField(
                        label = "Image",
                        imageUri = imageUri,
                        onImageSelected = { imageUri = it }
                    )

                    TriStateField(
                        label = "Three Options",
                        options = listOf("Low", "Medium", "High"),
                        selected = triState,
                        onSelectedChange = { triState = it }
                    )
                }
            }

            // ── Action buttons ─────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        shortText = ""
                        longText = ""
                        booleanValue = false
                        selectedDate = ""
                        selectedTime = ""
                        countValue = 0
                        listItems = listOf("")
                        imageUri = null
                        triState = -1
                    },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Text("Clear", style = MaterialTheme.typography.labelLarge)
                }

                Button(
                    onClick = onSave,
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
