package com.jorotayo.fl_datatracker.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Shapes as ShapesNew

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

/**
 * Material 3 Shapes
 *
 * Material 3 uses a simpler shape system with 5 sizes:
 * - Extra Small: 4dp (chips, small buttons)
 * - Small: 8dp (cards, text fields)
 * - Medium: 12dp (dialogs, menus)
 * - Large: 16dp (bottom sheets, navigation drawers)
 * - Extra Large: 28dp (large cards, FAB)
 */

val ShapesNew = ShapesNew(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)
