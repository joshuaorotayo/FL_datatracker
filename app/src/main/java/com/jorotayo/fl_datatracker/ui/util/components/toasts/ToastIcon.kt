package com.jorotayo.fl_datatracker.ui.util.components.toasts

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

// =============================================================================
// TOAST ICON — optional leading / trailing icon slot
// =============================================================================

/**
 * Defines an icon to show in the leading or trailing position of the toast.
 *
 * @param icon               The vector icon to display.
 * @param tint               Override tint colour. Null = use the mode's content colour.
 * @param contentDescription Accessibility label for the icon.
 */
data class ToastIcon(
    val icon: ImageVector,
    val tint: Color? = null,
    val contentDescription: String? = null
)