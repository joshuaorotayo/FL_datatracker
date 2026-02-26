package com.jorotayo.fl_datatracker.ui.screens.dataForm.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormEvent
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormState
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

@DefaultPreviews
@Composable
private fun PreviewDeletePresetDialog() {
    FL_DatatrackerThemeNew {
        DeletePresetDialog(
            state = DataFormState(presetToDelete = Preset(0L, "Example")),
            onEvent = {}
        )
    }
}

@Composable
fun DeletePresetDialog(
    modifier: Modifier = Modifier,
    onEvent: (DataFormEvent) -> Unit,
    state: DataFormState
) {
    AlertDialog(
        onDismissRequest = { onEvent(DataFormEvent.DismissDeletePresetDialog) },
        title = { Text("Delete Preset") },
        text = {
            Text("Delete \"${state.presetToDelete!!.presetName}\" and all its fields? This cannot be undone.")
        },
        confirmButton = {
            if (state.presetToDelete != null) {
                TextButton(onClick = { onEvent(DataFormEvent.ConfirmDeletePreset(state.presetToDelete)) }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            }

        },
        dismissButton = {
            TextButton(onClick = { onEvent(DataFormEvent.DismissDeletePresetDialog) }) {
                Text("Cancel")
            }
        }
    )
}