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

enum class DataFieldType(val value: Int, val type: String, val image: ImageVector) {
    SHORT_TEXT(value = 0, type = "Short Text", image = Icons.Default.HorizontalRule),
    LONG_TEXT(value = 1, type = "Long Text", image = Icons.Default.Notes),
    BOOLEAN(value = 2, type = "2 Options", image = Icons.Default.ToggleOn),
    DATE(value = 3, type = "Date", image = Icons.Default.EditCalendar),
    TIME(value = 4, type = "Time", image = Icons.Default.Schedule),
    COUNT(value = 5, type = "Count", image = Icons.Default.Pin),
    TRISTATE(value = 6, type = "3 Options", image = Icons.Default.MoreHoriz),
    IMAGE(value = 7, type = "Image", image = Icons.Default.Image),
    LIST(value = 8, type = "List", image = Icons.Default.List);

    override fun toString(): String {
        return type
    }

    companion object {
        fun getByValue(value: Int) = values().first { it.value == value }
    }
}
