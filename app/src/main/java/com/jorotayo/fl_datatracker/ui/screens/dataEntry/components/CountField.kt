package com.jorotayo.fl_datatracker.ui.screens.dataEntry.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

@DefaultPreviews
@Composable
private fun PreviewCountField() {
    FL_DatatrackerThemeNew {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CountField(label = "Editable", value = 42, onValueChange = {})
            CountField(label = "Read-only", value = 42, onValueChange = {}, enabled = false)
        }
    }
}

/**
 * A numeric counter field with + / − buttons and a directly-editable text input.
 *
 * @param enabled    When false the +/− buttons and text input are all disabled
 *                   and visually muted. The current value is still shown. Defaults to true.
 */
@Composable
fun CountField(
    modifier: Modifier = Modifier,
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    min: Int = 0,
    max: Int = 999_999,
    enabled: Boolean = true
) {
    var draftText by remember(value) { mutableStateOf(formatCount(value)) }

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilledTonalIconButton(
                onClick = {
                    val next = (value - 1).coerceAtLeast(min)
                    onValueChange(next)
                    draftText = formatCount(next)
                },
                // Disabled when at min OR when the whole field is read-only
                enabled = enabled && value > min,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease")
            }

            OutlinedTextField(
                value = draftText,
                onValueChange = { raw ->
                    if (enabled && raw.all { it.isDigit() || it == ',' }) {
                        draftText = raw
                        val parsed = raw.replace(",", "").toIntOrNull()
                        if (parsed != null) onValueChange(parsed.coerceIn(min, max))
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = enabled,
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(
                    onDone = {
                        val parsed = draftText.replace(",", "").toIntOrNull() ?: value
                        val clamped = parsed.coerceIn(min, max)
                        onValueChange(clamped)
                        draftText = formatCount(clamped)
                    }
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            FilledTonalIconButton(
                onClick = {
                    val next = (value + 1).coerceAtMost(max)
                    onValueChange(next)
                    draftText = formatCount(next)
                },
                enabled = enabled && value < max,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Increase")
            }
        }

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

private fun formatCount(value: Int): String = "%,d".format(value)