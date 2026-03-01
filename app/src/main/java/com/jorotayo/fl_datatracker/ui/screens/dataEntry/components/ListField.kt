package com.jorotayo.fl_datatracker.ui.screens.dataEntry.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

@DefaultPreviews
@Composable
private fun PreviewListField() {
    FL_DatatrackerThemeNew {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ListField(label = "Editable", items = listOf("Item 1", "Item 2"), onItemsChange = {})
            ListField(
                label = "Read-only",
                items = listOf("Item 1", "Item 2"),
                onItemsChange = {},
                enabled = false
            )
        }
    }
}

/**
 * A dynamic list field.
 *
 * @param enabled    When false all text inputs are read-only, delete buttons are
 *                   hidden, and no trailing empty row is appended. Defaults to true.
 */
@Composable
fun ListField(
    modifier: Modifier = Modifier,
    label: String,
    items: List<String>,
    onItemsChange: (List<String>) -> Unit,
    itemPlaceholder: String = "Add item…",
    maxItems: Int = 20,
    enabled: Boolean = true
) {
    val displayItems = remember(items, enabled) {
        val base = items.toMutableList()
        if (base.isEmpty()) base.add("")
        // Only append a trailing blank row when editing is allowed
        if (enabled && base.last().isNotEmpty() && base.size < maxItems) base.add("")
        base
    }

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

        displayItems.forEachIndexed { index, item ->
            val isTrailing = enabled && index == displayItems.lastIndex && item.isEmpty()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Row number badge
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(
                            if (enabled) MaterialTheme.colorScheme.secondaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (enabled)
                            MaterialTheme.colorScheme.onSecondaryContainer
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    )
                }

                OutlinedTextField(
                    value = item,
                    onValueChange = { newVal ->
                        if (!enabled) return@OutlinedTextField
                        val updated = displayItems.toMutableList()
                        updated[index] = newVal
                        val cleaned = updated.dropLastWhile { it.isEmpty() }
                        onItemsChange(cleaned.ifEmpty { listOf("") })
                    },
                    modifier = Modifier.weight(1f),
                    enabled = enabled,
                    placeholder = {
                        Text(itemPlaceholder, style = MaterialTheme.typography.bodyMedium)
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )

                // Delete icon — hidden entirely when disabled or on the trailing blank row
                if (!isTrailing && enabled) {
                    IconButton(
                        onClick = {
                            val updated = displayItems.toMutableList()
                            updated.removeAt(index)
                            val cleaned = updated.dropLastWhile { it.isEmpty() }
                            onItemsChange(cleaned.ifEmpty { listOf("") })
                        }
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Remove item",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }
        }

        if (enabled && displayItems.size >= maxItems) {
            Text(
                text = "Maximum of $maxItems items reached",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}