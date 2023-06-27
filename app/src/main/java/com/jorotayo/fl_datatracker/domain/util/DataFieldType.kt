package com.jorotayo.fl_datatracker.domain.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.ui.graphics.vector.ImageVector

enum class DataFieldType(val type: String, val image: ImageVector) {
    SHORT_TEXT(type = "Short Text", image = Icons.Default.HorizontalRule),
    LONG_TEXT(type = "Long Text", image = Icons.Default.Notes),
    BOOLEAN(type = "2 Options", image = Icons.Default.ToggleOn),
    DATE(type = "Date", image = Icons.Default.EditCalendar),
    TIME(type = "Time", image = Icons.Default.Schedule),
    COUNT(type = "Count", image = Icons.Default.Pin),
    TRISTATE(type = "3 Options", image = Icons.Default.MoreHoriz),
    IMAGE(type = "Image", image = Icons.Default.Image),
    LIST(type = "List", image = Icons.Default.List);


    override fun toString(): String {
        return type
    }
}
