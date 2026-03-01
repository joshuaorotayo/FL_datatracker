package com.jorotayo.fl_datatracker.ui.screens.dataEntry.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

@DefaultPreviews
@Composable
private fun PreviewShortTextField() {
    FL_DatatrackerThemeNew {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ShortTextField(label = "Editable", value = "Some value", onValueChange = {})
            ShortTextField(
                label = "Read-only",
                value = "Locked value",
                onValueChange = {},
                enabled = false
            )
        }
    }
}

/**
 * A labeled single-line text field with character counter.
 *
 * @param enabled    When false the field is read-only: input is blocked and
 *                   the field is visually muted. Defaults to true.
 */
@Composable
fun ShortTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = label,
    maxLength: Int = 50,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (enabled)
                MaterialTheme.colorScheme.onSurfaceVariant
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
        OutlinedTextField(
            value = value,
            onValueChange = { if (enabled && it.length <= maxLength) onValueChange(it) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
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