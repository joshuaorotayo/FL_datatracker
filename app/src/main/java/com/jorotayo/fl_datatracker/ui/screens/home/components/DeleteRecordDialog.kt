package com.jorotayo.fl_datatracker.ui.screens.home.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jorotayo.fl_datatracker.data.model.DataRecord
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.screens.home.HomeEvent
import com.jorotayo.fl_datatracker.ui.screens.home.HomeScreenState
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

@DefaultPreviews
@Composable
private fun PreviewDeleteRecordDialog() {
    FL_DatatrackerThemeNew {
        DeleteRecordDialog(
            state = HomeScreenState(
                recordToDelete = DataRecord(
                    recordId = 1,
                    presetId = 1,
                    title = "Morning Inspection"
                ),
                showDeleteDialog = true
            ),
            onEvent = {}
        )
    }
}

@Composable
fun DeleteRecordDialog(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    onEvent: (HomeEvent) -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(HomeEvent.DismissDeleteDialog) },
        title = { Text("Delete Record") },
        text = {
            val name = state.recordToDelete?.title?.ifBlank { "this record" } ?: "this record"
            Text("Delete \"$name\"? This cannot be undone.")
        },
        confirmButton = {
            if (state.recordToDelete != null) {
                TextButton(
                    onClick = { onEvent(HomeEvent.DeleteRecord(state.recordToDelete)) }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = { onEvent(HomeEvent.DismissDeleteDialog) }) {
                Text("Cancel")
            }
        }
    )
}