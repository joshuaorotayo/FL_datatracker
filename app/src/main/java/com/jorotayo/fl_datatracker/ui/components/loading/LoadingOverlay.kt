package com.jorotayo.fl_datatracker.ui.components.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

// =============================================================================
// PREVIEW
// =============================================================================

@DefaultPreviews
@Composable
private fun PreviewLoadingOverlay() {
    FL_DatatrackerThemeNew {
        LoadingOverlay()
    }
}

@Composable
fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        LoadingCard()
    }
}