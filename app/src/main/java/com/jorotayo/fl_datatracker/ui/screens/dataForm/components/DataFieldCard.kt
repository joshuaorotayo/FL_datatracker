package com.jorotayo.fl_datatracker.ui.screens.dataForm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jorotayo.fl_datatracker.ui.util.Dimensions.iconLarge
import com.jorotayo.fl_datatracker.ui.util.Dimensions.iconSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMedium
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMediumLarge
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingNone
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXXSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXXXSmall

@Composable
fun DataFieldCard(
    modifier: Modifier = Modifier,
    field: DataFieldUi,
    onDelete: () -> Unit,
    onToggleActive: () -> Unit,
    onHintUpdate: (String) -> Unit = {},
    onBooleanOptionsUpdate: (List<String>) -> Unit = {},
    onTristateOptionsUpdate: (List<String>) -> Unit = {}
) {
    var isActive by remember { mutableStateOf(field.isActive) }
    var showHintDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isActive) spacingXXXSmall else spacingNone
        )
    ) {
        Column(
            modifier = Modifier.padding(spacingMedium),
            verticalArrangement = Arrangement.spacedBy(spacingSmall)
        ) {
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(spacingSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        Icon(
                            imageVector = field.type.icon,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(spacingXSmall)
                                .size(spacingMediumLarge)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = field.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = if (isActive) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                        Text(
                            text = field.type.displayName,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(spacingXXSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        isActive = !isActive
                        onToggleActive()
                    }) {
                        Icon(
                            imageVector = if (isActive) {
                                Icons.Default.CheckCircle
                            } else {
                                Icons.Default.RadioButtonUnchecked
                            },
                            contentDescription = if (isActive) "Active" else "Inactive",
                            tint = if (isActive) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }

                    IconButton(onClick = onDelete) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            // Type-specific section
            when (field.type) {
                FieldType.BOOLEAN -> BooleanOptionsSection(
                    options = field.booleanOptions,
                    onEdit = { showHintDialog = true }
                )

                FieldType.TRISTATE -> TristateOptionsSection(
                    options = field.tristateOptions,
                    onEdit = { showHintDialog = true }
                )

                else -> RegularHintSection(
                    hint = field.hint,
                    onEdit = { showHintDialog = true }
                )
            }
        }
    }

    if (showHintDialog) {
        when (field.type) {
            FieldType.BOOLEAN -> BooleanOptionsDialog(
                initialOptions = field.booleanOptions,
                onDismiss = { showHintDialog = false },
                onSave = { options ->
                    onBooleanOptionsUpdate(options);
                    showHintDialog = false
                }
            )

            FieldType.TRISTATE -> TristateOptionsDialog(
                initialOptions = field.tristateOptions,
                onDismiss = { showHintDialog = false },
                onSave = { options ->
                    onTristateOptionsUpdate(options);
                    showHintDialog = false
                }
            )

            else -> TextHintDialog(
                initialHint = field.hint,
                fieldName = field.name,
                onDismiss = { showHintDialog = false },
                onSave = { newHint ->
                    onHintUpdate(newHint);
                    showHintDialog = false
                }
            )
        }
    }
}

@Composable
internal fun RegularHintSection(hint: String, onEdit: () -> Unit) {
    if (hint.isNotEmpty()) {
        Divider(color = MaterialTheme.colorScheme.outlineVariant)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacingXSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hint:",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = hint,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onEdit, modifier = Modifier.size(iconLarge)) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit hint",
                    modifier = Modifier.size(iconSmall),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    } else {
        TextButton(onClick = onEdit, modifier = Modifier.fillMaxWidth()) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(spacingMedium)
            )
            Spacer(modifier = Modifier.width(spacingXXSmall))
            Text("Add hint", style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
internal fun BooleanOptionsSection(options: List<String>, onEdit: () -> Unit) {
    Divider(color = MaterialTheme.colorScheme.outlineVariant)
    if (options.size == 2) {
        Surface(
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = spacingSmall, vertical = spacingXSmall),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(spacingXSmall),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(iconSmall)
                    )
                    Text(
                        text = "Options: ${options[0]} / ${options[1]}",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                IconButton(onClick = onEdit, modifier = Modifier.size(iconLarge)) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit options",
                        modifier = Modifier.size(iconSmall)
                    )
                }
            }
        }
    } else {
        TextButton(onClick = onEdit, modifier = Modifier.fillMaxWidth()) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(spacingMedium)
            )
            Spacer(modifier = Modifier.width(spacingXXSmall))
            Text("Add boolean options", style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
internal fun TristateOptionsSection(options: List<String>, onEdit: () -> Unit) {
    Divider(color = MaterialTheme.colorScheme.outlineVariant)
    if (options.size == 3) {
        Surface(
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = spacingSmall, vertical = spacingXSmall),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(spacingXSmall),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(iconSmall)
                    )
                    Text(
                        text = "Options: ${options[0]} / ${options[1]} / ${options[2]}",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                IconButton(onClick = onEdit, modifier = Modifier.size(iconLarge)) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit options",
                        modifier = Modifier.size(iconSmall)
                    )
                }
            }
        }
    } else {
        TextButton(onClick = onEdit, modifier = Modifier.fillMaxWidth()) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(spacingMedium)
            )
            Spacer(modifier = Modifier.width(spacingXXSmall))
            Text("Add tristate options", style = MaterialTheme.typography.labelMedium)
        }
    }
}
