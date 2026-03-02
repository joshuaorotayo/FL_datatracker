package com.jorotayo.fl_datatracker.ui.screens.dataEntry.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

@DefaultPreviews
@Composable
private fun PreviewRecordNameField() {
    FL_DatatrackerThemeNew {
        Column {
            RecordNameField(value = "Morning Inspection", enabled = true, onValueChange = {})
            RecordNameField(value = "Read-only record", enabled = false, onValueChange = {})
        }
    }
}

@Composable
fun RecordNameField(
    value: String,
    enabled: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Record name") },
            placeholder = { Text("e.g. Morning Inspection") },
            singleLine = true,
            enabled = enabled,
            shape = MaterialTheme.shapes.medium
        )
    }
}