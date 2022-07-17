package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SimpleIconButton(
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier,
    tint: Color,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = modifier,
            imageVector = icon,
            tint = tint,
            contentDescription = contentDescription
        )
    }
}