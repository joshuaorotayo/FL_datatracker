package com.jorotayo.fl_datatracker.ui.screens.dataForm.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

/**
 * DELETE FIELD CONFIRMATION DIALOG
 */

// =============================================================================
// PREVIEW
// =============================================================================

@DefaultPreviews
@Composable
fun PreviewDataFormScreen() {
    FL_DatatrackerThemeNew {
        DeleteFieldDialog(
            fieldName = "Name",
            onConfirm = {},
            onDismiss = {}
        )
    }
}

@Composable
fun DeleteFieldDialog(
    fieldName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Delete Field?",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete \"$fieldName\"? This action cannot be undone.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}