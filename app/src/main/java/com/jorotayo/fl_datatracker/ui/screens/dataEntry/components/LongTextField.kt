package com.jorotayo.fl_datatracker.ui.screens.dataEntry.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
private fun PreviewLongTextField() {
    FL_DatatrackerThemeNew {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LongTextField(label = "Editable", value = "Some longer text here", onValueChange = {})
            LongTextField(
                label = "Read-only",
                value = "Locked longer text",
                onValueChange = {},
                enabled = false
            )
        }
    }
}

/**
 * A labeled multi-line text field with character counter.
 *
 * @param enabled    When false the field is read-only: input is blocked and
 *                   the field is visually muted. Defaults to true.
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
    maxLines: Int = 6,
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
            modifier = Modifier
                .fillMaxWidth()
                .height(if (minLines >= 4) 120.dp else (minLines * 32).dp),
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
            minLines = minLines,
            maxLines = maxLines,
            shape = MaterialTheme.shapes.medium
        )
    }
}