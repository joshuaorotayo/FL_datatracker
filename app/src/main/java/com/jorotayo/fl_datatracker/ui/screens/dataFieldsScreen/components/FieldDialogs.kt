package com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMediumLarge
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXXSmall

@Composable
fun TextHintDialog(
    initialHint: String,
    fieldName: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var hint by remember { mutableStateOf(initialHint) }
    val maxChars = 100

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Edit Hint",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(spacingXXSmall)) {
                Text(
                    text = "Field: $fieldName",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OutlinedTextField(
                    value = hint,
                    onValueChange = { if (it.length <= maxChars) hint = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Hint Text") },
                    placeholder = { Text("Enter hint text for $fieldName") },
                    supportingText = {
                        Text(
                            text = "${hint.length} / $maxChars",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    minLines = 2,
                    maxLines = 4,
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(hint) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun BooleanOptionsDialog(
    initialOptions: List<String>,
    onDismiss: () -> Unit,
    onSave: (List<String>) -> Unit
) {
    var option1 by remember { mutableStateOf(initialOptions.getOrNull(0) ?: "") }
    var option2 by remember { mutableStateOf(initialOptions.getOrNull(1) ?: "") }
    val focusManager = LocalFocusManager.current
    val maxChars = 20

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Boolean Options",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(spacingMediumLarge)) {
                Text(
                    text = "Enter the two options for this field (e.g., Yes / No)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OptionTextField(
                    value = option1,
                    onValueChange = { if (it.length <= maxChars) option1 = it },
                    label = "First Option",
                    placeholder = "Yes",
                    maxChars = maxChars,
                    imeAction = ImeAction.Next,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                )
                OptionTextField(
                    value = option2,
                    onValueChange = { if (it.length <= maxChars) option2 = it },
                    label = "Second Option",
                    placeholder = "No",
                    maxChars = maxChars,
                    imeAction = ImeAction.Done,
                    onImeAction = { focusManager.clearFocus() }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(listOf(option1, option2)) },
                enabled = option1.isNotBlank() && option2.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun TristateOptionsDialog(
    initialOptions: List<String>,
    onDismiss: () -> Unit,
    onSave: (List<String>) -> Unit
) {
    var option1 by remember { mutableStateOf(initialOptions.getOrNull(0) ?: "") }
    var option2 by remember { mutableStateOf(initialOptions.getOrNull(1) ?: "") }
    var option3 by remember { mutableStateOf(initialOptions.getOrNull(2) ?: "") }
    val focusManager = LocalFocusManager.current
    val maxChars = 20

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Tristate Options",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(spacingMediumLarge)) {
                Text(
                    text = "Enter the three options for this field (e.g., No / Maybe / Yes)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OptionTextField(
                    value = option1,
                    onValueChange = { if (it.length <= maxChars) option1 = it },
                    label = "First Option",
                    placeholder = "No",
                    maxChars = maxChars,
                    imeAction = ImeAction.Next,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                )
                OptionTextField(
                    value = option2,
                    onValueChange = { if (it.length <= maxChars) option2 = it },
                    label = "Second Option",
                    placeholder = "Maybe",
                    maxChars = maxChars,
                    imeAction = ImeAction.Next,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                )
                OptionTextField(
                    value = option3,
                    onValueChange = { if (it.length <= maxChars) option3 = it },
                    label = "Third Option",
                    placeholder = "Yes",
                    maxChars = maxChars,
                    imeAction = ImeAction.Done,
                    onImeAction = { focusManager.clearFocus() }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(listOf(option1, option2, option3)) },
                enabled = option1.isNotBlank() && option2.isNotBlank() && option3.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

/** Shared text field used inside option dialogs to avoid repetition. */
@Composable
private fun OptionTextField(
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
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() },
            onDone = { onImeAction() }
        )
    )
}