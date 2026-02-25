package com.jorotayo.fl_datatracker.ui.screens.dataForm.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import com.jorotayo.fl_datatracker.ui.util.Dimensions.iconSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMedium
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMediumLarge
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXXSmall

// =============================================================================
// PREVIEW
// =============================================================================

@OptIn(ExperimentalMaterial3Api::class)
@DefaultPreviews
@Composable
fun PreviewAddDataFieldSheet() {
    FL_DatatrackerThemeNew {
        var show by remember { mutableStateOf(true) }
        if (show) {
            AddDataFieldSheet(
                onDismiss = { show = false },
                onSave = { _ -> show = false }
            )
        }
    }
}

// =============================================================================
// BOTTOM SHEET
// =============================================================================

/**
 * Bottom sheet for creating a new [DataFieldUi].
 *
 * The sheet walks through three steps in order:
 *  1. Field name
 *  2. Field type selection (grid of all [FieldType] options)
 *  3. Type-specific extras — hint text, boolean options, or tristate options
 *
 * [onSave] is called with the completed [DataFieldUi] when the user confirms.
 * Wire this to [DataFormEvent.AddField] in the screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDataFieldSheet(
    onDismiss: () -> Unit,
    onSave: (DataFieldUi) -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    // ── Form state ────────────────────────────────────────────────────────────
    var fieldName by remember { mutableStateOf("") }
    var fieldNameError by remember { mutableStateOf<String?>(null) }
    var selectedType by remember { mutableStateOf<FieldType?>(null) }
    var typeError by remember { mutableStateOf<String?>(null) }

    // Type-specific extras
    var hint by remember { mutableStateOf("") }
    var boolOpt1 by remember { mutableStateOf("") }
    var boolOpt2 by remember { mutableStateOf("") }
    var triOpt1 by remember { mutableStateOf("") }
    var triOpt2 by remember { mutableStateOf("") }
    var triOpt3 by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = spacingMedium)
                .padding(bottom = spacingMedium),
            verticalArrangement = Arrangement.spacedBy(spacingMediumLarge)
        ) {
            // ── Title ─────────────────────────────────────────────────────────
            Text(
                text = "New Data Field",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Divider(color = MaterialTheme.colorScheme.outlineVariant)

            // ── Step 1: Field name ────────────────────────────────────────────
            StepSection(number = 1, title = "Field Name") {
                OutlinedTextField(
                    value = fieldName,
                    onValueChange = {
                        fieldName = it
                        if (it.isNotBlank()) fieldNameError = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("e.g. Notes, Score, Date taken…") },
                    isError = fieldNameError != null,
                    supportingText = {
                        fieldNameError?.let {
                            InlineError(message = it)
                        }
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        errorBorderColor = MaterialTheme.colorScheme.error
                    ),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = androidx.compose.foundation.text.KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
            }

            // ── Step 2: Field type ────────────────────────────────────────────
            StepSection(number = 2, title = "Field Type") {
                Column(verticalArrangement = Arrangement.spacedBy(spacingXSmall)) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxWidth()
                            // Fixed height so it doesn't conflict with the outer scroll
                            .height(220.dp),
                        horizontalArrangement = Arrangement.spacedBy(spacingXSmall),
                        verticalArrangement = Arrangement.spacedBy(spacingXSmall)
                    ) {
                        items(FieldType.entries) { type ->
                            FieldTypeChip(
                                type = type,
                                isSelected = selectedType == type,
                                onClick = {
                                    selectedType = type
                                    typeError = null
                                    // Reset extras when type changes
                                    hint = ""; boolOpt1 = ""; boolOpt2 = ""
                                    triOpt1 = ""; triOpt2 = ""; triOpt3 = ""
                                }
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = typeError != null,
                        enter = expandVertically(tween(200)) + fadeIn(tween(200)),
                        exit = shrinkVertically(tween(150)) + fadeOut(tween(150))
                    ) {
                        typeError?.let { InlineError(message = it) }
                    }
                }
            }

            // ── Step 3: Type-specific extras (only shown once type is picked) ─
            AnimatedVisibility(
                visible = selectedType != null,
                enter = expandVertically(tween(250)) + fadeIn(tween(250)),
                exit = shrinkVertically(tween(200)) + fadeOut(tween(200))
            ) {
                selectedType?.let { type ->
                    StepSection(number = 3, title = extrasTitle(type)) {
                        when (type) {
                            FieldType.BOOLEAN -> BooleanExtras(
                                opt1 = boolOpt1,
                                opt2 = boolOpt2,
                                onOpt1Change = { boolOpt1 = it },
                                onOpt2Change = { boolOpt2 = it }
                            )

                            FieldType.TRISTATE -> TristateExtras(
                                opt1 = triOpt1,
                                opt2 = triOpt2,
                                opt3 = triOpt3,
                                onOpt1Change = { triOpt1 = it },
                                onOpt2Change = { triOpt2 = it },
                                onOpt3Change = { triOpt3 = it }
                            )

                            else -> HintExtra(
                                hint = hint,
                                fieldName = fieldName,
                                onHintChange = { hint = it }
                            )
                        }
                    }
                }
            }

            // ── Actions ───────────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacingXSmall),
                horizontalArrangement = Arrangement.spacedBy(spacingSmall)
            ) {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel", style = MaterialTheme.typography.labelLarge)
                }

                Button(
                    onClick = {
                        // Validate
                        var valid = true
                        if (fieldName.isBlank()) {
                            fieldNameError = "Field name cannot be blank."
                            valid = false
                        }
                        if (selectedType == null) {
                            typeError = "Please select a field type."
                            valid = false
                        }
                        if (!valid) return@Button

                        onSave(
                            DataFieldUi(
                                name = fieldName.trim(),
                                type = selectedType!!,
                                hint = hint.trim(),
                                booleanOptions = if (selectedType == FieldType.BOOLEAN)
                                    listOf(
                                        boolOpt1.trim(),
                                        boolOpt2.trim()
                                    ).filter { it.isNotBlank() }
                                else emptyList(),
                                tristateOptions = if (selectedType == FieldType.TRISTATE)
                                    listOf(
                                        triOpt1.trim(),
                                        triOpt2.trim(),
                                        triOpt3.trim()
                                    ).filter { it.isNotBlank() }
                                else emptyList(),
                                isActive = true
                            )
                        )
                    },
                    modifier = Modifier.weight(2f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(iconSmall)
                    )
                    Spacer(modifier = Modifier.width(spacingXXSmall))
                    Text("Create Field", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

// =============================================================================
// FIELD TYPE CHIP — grid tile for type selection
// =============================================================================

@Composable
private fun FieldTypeChip(
    type: FieldType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColour = if (isSelected)
        MaterialTheme.colorScheme.primaryContainer
    else
        MaterialTheme.colorScheme.surfaceVariant

    val contentColour = if (isSelected)
        MaterialTheme.colorScheme.onPrimaryContainer
    else
        MaterialTheme.colorScheme.onSurfaceVariant

    val borderColour = if (isSelected)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.outlineVariant

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(bgColour)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = borderColour,
                shape = MaterialTheme.shapes.medium
            )
            .clickable(onClick = onClick)
            .padding(vertical = spacingSmall, horizontal = spacingXXSmall),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacingXXSmall)
        ) {
            Icon(
                imageVector = type.icon,
                contentDescription = null,
                tint = contentColour,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = type.displayName,
                style = MaterialTheme.typography.labelSmall,
                color = contentColour,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
            if (isSelected) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(14.dp)
                )
            } else {
                Spacer(modifier = Modifier.size(14.dp))
            }
        }
    }
}

// =============================================================================
// STEP SECTION WRAPPER
// =============================================================================

@Composable
private fun StepSection(
    number: Int,
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(spacingXSmall)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacingXSmall)
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(24.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "$number",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        content()
    }
}

// =============================================================================
// TYPE-SPECIFIC EXTRAS
// =============================================================================

@Composable
private fun HintExtra(
    hint: String,
    fieldName: String,
    onHintChange: (String) -> Unit
) {
    val maxChars = 100
    OutlinedTextField(
        value = hint,
        onValueChange = { if (it.length <= maxChars) onHintChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                if (fieldName.isNotBlank()) "Hint for \"$fieldName\"…"
                else "Optional hint for data entry…"
            )
        },
        supportingText = {
            Text(
                text = "${hint.length} / $maxChars",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelSmall
            )
        },
        minLines = 2,
        maxLines = 3,
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
private fun BooleanExtras(
    opt1: String,
    opt2: String,
    onOpt1Change: (String) -> Unit,
    onOpt2Change: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val maxChars = 20
    Column(verticalArrangement = Arrangement.spacedBy(spacingXSmall)) {
        Text(
            text = "Define the two selectable options",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OptionField(
            value = opt1,
            onValueChange = { if (it.length <= maxChars) onOpt1Change(it) },
            label = "First Option",
            placeholder = "Yes",
            maxChars = maxChars,
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
        )
        OptionField(
            value = opt2,
            onValueChange = { if (it.length <= maxChars) onOpt2Change(it) },
            label = "Second Option",
            placeholder = "No",
            maxChars = maxChars,
            imeAction = ImeAction.Done,
            onImeAction = { focusManager.clearFocus() }
        )
    }
}

@Composable
private fun TristateExtras(
    opt1: String,
    opt2: String,
    opt3: String,
    onOpt1Change: (String) -> Unit,
    onOpt2Change: (String) -> Unit,
    onOpt3Change: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val maxChars = 20
    Column(verticalArrangement = Arrangement.spacedBy(spacingXSmall)) {
        Text(
            text = "Define the three selectable options",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OptionField(
            value = opt1,
            onValueChange = { if (it.length <= maxChars) onOpt1Change(it) },
            label = "First Option",
            placeholder = "Low",
            maxChars = maxChars,
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
        )
        OptionField(
            value = opt2,
            onValueChange = { if (it.length <= maxChars) onOpt2Change(it) },
            label = "Second Option",
            placeholder = "Medium",
            maxChars = maxChars,
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
        )
        OptionField(
            value = opt3,
            onValueChange = { if (it.length <= maxChars) onOpt3Change(it) },
            label = "Third Option",
            placeholder = "High",
            maxChars = maxChars,
            imeAction = ImeAction.Done,
            onImeAction = { focusManager.clearFocus() }
        )
    }
}

@Composable
private fun OptionField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    maxChars: Int,
    imeAction: ImeAction,
    onImeAction: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        supportingText = {
            Text(
                text = "${value.length} / $maxChars",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelSmall
            )
        },
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(imeAction = imeAction),
        keyboardActions = androidx.compose.foundation.text.KeyboardActions(
            onNext = { onImeAction() },
            onDone = { onImeAction() }
        )
    )
}

// =============================================================================
// INLINE ERROR — matches FieldErrorRow style from the data entry screen
// =============================================================================

@Composable
private fun InlineError(message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacingXXSmall)
    ) {
        Icon(
            Icons.Default.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}

// =============================================================================
// HELPERS
// =============================================================================

private fun extrasTitle(type: FieldType): String = when (type) {
    FieldType.BOOLEAN -> "Boolean Options"
    FieldType.TRISTATE -> "Tristate Options"
    else -> "Hint (Optional)"
}