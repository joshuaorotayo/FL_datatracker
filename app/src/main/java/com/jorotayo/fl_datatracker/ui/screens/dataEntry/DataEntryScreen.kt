package com.jorotayo.fl_datatracker.ui.screens.dataEntry

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

// =============================================================================
// EXISTING FIELDS — SPLIT INTO REUSABLE COMPOSABLES
// =============================================================================

/**
 * A labelled single-line text field with character counter.
 *
 * @param label      Field label shown above the input.
 * @param value      Current text value.
 * @param onValueChange Callback when the user edits the text.
 * @param placeholder Hint text shown when the field is empty.
 * @param maxLength  Maximum allowed characters (default 50).
 */
@Composable
fun ShortTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = label,
    maxLength: Int = 50
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OutlinedTextField(
            value = value,
            onValueChange = { if (it.length <= maxLength) onValueChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(placeholder, style = MaterialTheme.typography.bodyMedium)
            },
            supportingText = {
                Text(
                    text = "${value.length} / $maxLength",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )
    }
}

/**
 * A labelled multi-line text field with character counter.
 *
 * @param label      Field label shown above the input.
 * @param value      Current text value.
 * @param onValueChange Callback when the user edits the text.
 * @param placeholder Hint text shown when the field is empty.
 * @param maxLength  Maximum allowed characters (default 200).
 * @param minLines   Minimum visible lines (default 4).
 * @param maxLines   Maximum visible lines (default 6).
 */
@Composable
fun LongTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Please enter content for field: $label",
    maxLength: Int = 200,
    minLines: Int = 4,
    maxLines: Int = 6
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OutlinedTextField(
            value = value,
            onValueChange = { if (it.length <= maxLength) onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(if (minLines >= 4) 120.dp else (minLines * 32).dp),
            placeholder = {
                Text(placeholder, style = MaterialTheme.typography.bodyMedium)
            },
            supportingText = {
                Text(
                    text = "${value.length} / $maxLength",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            minLines = minLines,
            maxLines = maxLines,
            shape = MaterialTheme.shapes.medium
        )
    }
}

/**
 * A Yes / No toggle field using FilterChips.
 *
 * @param label    Field label shown above the chips.
 * @param value    Current boolean selection (true = Yes, false = No).
 * @param onValueChange Callback when the user taps a chip.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooleanField(
    label: String,
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = value,
                onClick = { onValueChange(true) },
                label = { Text("Yes", style = MaterialTheme.typography.labelLarge) },
                modifier = Modifier.weight(1f),
                leadingIcon = if (value) {
                    {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else {
                    null
                }
            )
            FilterChip(
                selected = !value,
                onClick = { onValueChange(false) },
                label = { Text("No", style = MaterialTheme.typography.labelLarge) },
                modifier = Modifier.weight(1f),
                leadingIcon = if (!value) {
                    {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else {
                    null
                }
            )
        }
    }
}

/**
 * A date picker trigger field.
 *
 * @param label      Field label shown above the button.
 * @param value      Currently selected date string (empty = show placeholder).
 * @param onPickDate Callback when the user taps to pick a date.
 */
@Composable
fun DateField(
    label: String,
    value: String,
    onPickDate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OutlinedButton(
            onClick = onPickDate,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = value.ifEmpty { "DDnd Month, Year" },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
        }
    }
}

/**
 * A time picker trigger field.
 *
 * @param label      Field label shown above the button.
 * @param value      Currently selected time string (empty = show placeholder).
 * @param onPickTime Callback when the user taps to pick a time.
 */
@Composable
fun TimeField(
    label: String,
    value: String,
    onPickTime: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OutlinedButton(
            onClick = onPickTime,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = value.ifEmpty { "HH:MM" },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
        }
    }
}

// =============================================================================
// NEW FIELDS
// =============================================================================

/**
 * A numeric counter field with + / − buttons and a directly-editable text input.
 * Range: 0 – 999,999 by default. Large numbers are formatted with comma separators.
 *
 * @param label         Field label shown above the counter row.
 * @param value         Current integer count.
 * @param onValueChange Callback with the new count.
 * @param min           Minimum allowed value (default 0).
 * @param max           Maximum allowed value (default 999,999).
 */
@Composable
fun CountField(
    modifier: Modifier = Modifier,
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    min: Int = 0,
    max: Int = 999_999
) {
    // Draft holds the raw string the user is typing so we don't fight them mid-edit.
    // Re-seeds from value only when value changes externally (e.g. +/- buttons).
    var draftText by remember(value) { mutableStateOf(formatCount(value)) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // − button
            FilledTonalIconButton(
                onClick = {
                    val next = (value - 1).coerceAtLeast(min)
                    onValueChange(next)
                    draftText = formatCount(next)
                },
                enabled = value > min,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease")
            }

            // Editable number input — strips commas before parsing so the user
            // can also type a raw number without commas.
            OutlinedTextField(
                value = draftText,
                onValueChange = { raw ->
                    // Allow digits and commas only while typing
                    if (raw.all { it.isDigit() || it == ',' }) {
                        draftText = raw
                        val parsed = raw.replace(",", "").toIntOrNull()
                        if (parsed != null) {
                            onValueChange(parsed.coerceIn(min, max))
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // On keyboard confirm, clamp and reformat
                        val parsed = draftText.replace(",", "").toIntOrNull() ?: value
                        val clamped = parsed.coerceIn(min, max)
                        onValueChange(clamped)
                        draftText = formatCount(clamped)
                    }
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            // + button
            FilledTonalIconButton(
                onClick = {
                    val next = (value + 1).coerceAtMost(max)
                    onValueChange(next)
                    draftText = formatCount(next)
                },
                enabled = value < max,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Increase")
            }
        }

        // Range hint — only shown when non-default bounds are set
        if (min != 0 || max != 999_999) {
            Text(
                text = "Range: ${formatCount(min)} – ${formatCount(max)}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}

/** Formats an integer with thousands-separator commas, e.g. 12345 → "12,345". */
private fun formatCount(value: Int): String =
    "%,d".format(value)

/**
 * A dynamic list field.
 *
 * Starts with one input box. A new row is revealed automatically once the
 * previous row has content. Each non-empty row shows a delete icon.
 *
 * @param label   Field label shown above the list.
 * @param items   Current list of string values.
 * @param onItemsChange Callback with the full updated list.
 * @param itemPlaceholder Placeholder text for each row.
 * @param maxItems Maximum number of items allowed (default 20).
 */
@Composable
fun ListField(
    label: String,
    items: List<String>,
    onItemsChange: (List<String>) -> Unit,
    itemPlaceholder: String = "Add item…",
    maxItems: Int = 20,
    modifier: Modifier = Modifier
) {
    // Always expose at least one row; append a blank trailing row when the last non-blank
    // entry is filled in (and we haven't hit the cap).
    val displayItems = remember(items) {
        val base = items.toMutableList()
        if (base.isEmpty()) base.add("")
        if (base.last().isNotEmpty() && base.size < maxItems) base.add("")
        base
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        displayItems.forEachIndexed { index, item ->
            val isTrailing = index == displayItems.lastIndex && item.isEmpty()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Row number badge
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                OutlinedTextField(
                    value = item,
                    onValueChange = { newVal ->
                        val updated = displayItems.toMutableList()
                        updated[index] = newVal
                        // Strip any trailing blank that was auto-added (we'll re-add in next compose)
                        val cleaned = updated.dropLastWhile { it.isEmpty() }
                        onItemsChange(cleaned.ifEmpty { listOf("") })
                    },
                    modifier = Modifier.weight(1f),
                    placeholder = {
                        Text(itemPlaceholder, style = MaterialTheme.typography.bodyMedium)
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )

                // Delete icon — only shown for filled rows
                if (!isTrailing) {
                    IconButton(
                        onClick = {
                            val updated = displayItems.toMutableList()
                            updated.removeAt(index)
                            val cleaned = updated.dropLastWhile { it.isEmpty() }
                            onItemsChange(cleaned.ifEmpty { listOf("") })
                        }
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Remove item",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else {
                    // Placeholder spacer so layout stays stable
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }
        }

        if (displayItems.size >= maxItems) {
            Text(
                text = "Maximum of $maxItems items reached",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

/**
 * An image picker field.
 *
 * Shows a preview once an image is selected. Offers two actions: pick from
 * gallery or capture with camera. Replaces the preview on each selection.
 *
 * @param label          Field label shown above the preview area.
 * @param imageUri       Currently selected image URI (null = no selection).
 * @param onImageSelected Callback with the newly selected URI.
 */
@Composable
fun ImageField(
    label: String,
    imageUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> uri?.let(onImageSelected) }

    // Camera requires a pre-created file URI; simplified here to gallery + camera intent.
    // For a full implementation wire in FileProvider and TakePicture contract.
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        // bitmap is a thumbnail; for production use TakePicture with a FileProvider URI
        // and call onImageSelected with that URI. Omitted here to avoid FileProvider setup.
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Preview area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { galleryLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Selected image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Tap to select an image",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Icon(
                    Icons.Default.Image,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Gallery", style = MaterialTheme.typography.labelLarge)
            }
            OutlinedButton(
                onClick = { cameraLauncher.launch(null) },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Icon(
                    Icons.Default.AddAPhoto,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Camera", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

/**
 * A tri-state selection field — exactly one of three labelled options can be active.
 *
 * @param label     Field label shown above the chips row.
 * @param options   Exactly three option labels (e.g. listOf("Low", "Medium", "High")).
 * @param selected  Index of the currently selected option (0, 1, or 2). -1 = none.
 * @param onSelectedChange Callback with the newly selected index.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriStateField(
    label: String,
    options: List<String>,
    selected: Int,
    onSelectedChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    require(options.size == 3) { "TriStateField requires exactly 3 options." }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Using repeat + index so each chip is a direct child of RowScope,
            // keeping Modifier.weight(1f) valid and hit-testing intact.
            repeat(options.size) { index ->
                val isSelected = selected == index
                FilterChip(
                    selected = isSelected,
                    onClick = { onSelectedChange(index) },
                    label = {
                        Text(
                            text = options[index],
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    modifier = Modifier.weight(1f),
                    leadingIcon = if (isSelected) {
                        {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
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

// =============================================================================
// PREVIEW — FULL SCREEN EXERCISING ALL COMPONENTS
// =============================================================================

@DefaultPreviews
@Composable
fun PreviewAllFormFields() {
    FL_DatatrackerThemeNew {
        AllFieldsPreviewScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllFieldsPreviewScreen(
    onNavigateBack: () -> Unit = {},
    onSave: () -> Unit = {}
) {
    // State for every field
    var shortText by remember { mutableStateOf("") }
    var longText by remember { mutableStateOf("") }
    var booleanValue by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var countValue by remember { mutableIntStateOf(0) }
    var listItems by remember { mutableStateOf(listOf("")) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var triState by remember { mutableIntStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Form Fields", style = MaterialTheme.typography.titleLarge) },
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
            // Info banner
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Preview of all reusable form field composables",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // Form card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "Form Fields",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Divider(color = MaterialTheme.colorScheme.outlineVariant)

                    // ── Existing fields ────────────────────────────────────────
                    ShortTextField(
                        label = "Short Text",
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
                        onPickDate = { /* launch date picker */ }
                    )

                    TimeField(
                        label = "Time",
                        value = selectedTime,
                        onPickTime = { /* launch time picker */ }
                    )

                    Divider(color = MaterialTheme.colorScheme.outlineVariant)

                    // ── New fields ─────────────────────────────────────────────
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

            // Action buttons
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
