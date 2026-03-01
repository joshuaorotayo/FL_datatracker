package com.jorotayo.fl_datatracker.ui.screens.dataEntry.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

@DefaultPreviews
@Composable
private fun PreviewBooleanField() {
    FL_DatatrackerThemeNew {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BooleanField(label = "Editable", value = true, onValueChange = {})
            BooleanField(label = "Read-only", value = true, onValueChange = {}, enabled = false)
        }
    }
}

/**
 * A Yes / No toggle field using FilterChips.
 *
 * @param enabled    When false chips are non-interactive and visually muted.
 *                   The current selection is still shown. Defaults to true.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooleanField(
    label: String,
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (enabled)
                MaterialTheme.colorScheme.onSurfaceVariant
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = value,
                onClick = { if (enabled) onValueChange(true) },
                label = { Text("Yes", style = MaterialTheme.typography.labelLarge) },
                modifier = Modifier.weight(1f),
                enabled = enabled,
                leadingIcon = if (value) {
                    {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else null
            )
            FilterChip(
                selected = !value,
                onClick = { if (enabled) onValueChange(false) },
                label = { Text("No", style = MaterialTheme.typography.labelLarge) },
                modifier = Modifier.weight(1f),
                enabled = enabled,
                leadingIcon = if (!value) {
                    {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else null
            )
        }
    }
}