package com.jorotayo.fl_datatracker.ui.screens.dataForm.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShortText
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * UI-layer model for a data field definition.
 * Distinct from the ObjectBox DataField entity — this is what the screen works with.
 * Convert to/from the entity via DataFieldMapper.
 */
data class DataFieldUi(
    val id: Long = 0,
    val name: String,
    val type: FieldType,
    val hint: String = "",
    val booleanOptions: List<String> = emptyList(),
    val tristateOptions: List<String> = emptyList(),
    val isActive: Boolean = true
)

enum class FieldType(val displayName: String, val icon: ImageVector) {
    SHORT_TEXT("Short Text", Icons.Default.ShortText),
    LONG_TEXT("Long Text", Icons.Default.Notes),
    BOOLEAN("2 Options", Icons.Default.ToggleOn),
    DATE("Date", Icons.Default.DateRange),
    TIME("Time", Icons.Default.Schedule),
    COUNT("Count", Icons.Default.Numbers),
    TRISTATE("Triple Choice", Icons.Default.MoreHoriz),
    IMAGE("Image", Icons.Default.Image),
    LIST("List", Icons.Default.List)
}