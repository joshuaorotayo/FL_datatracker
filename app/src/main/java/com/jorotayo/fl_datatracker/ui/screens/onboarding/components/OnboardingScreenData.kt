package com.jorotayo.fl_datatracker.ui.screens.onboarding.components

import androidx.compose.ui.graphics.vector.ImageVector

// =============================================================================
// DATA CLASS — supports both a vector icon and an optional image resource.
// Set imageRes to a drawable res ID for a real image, or leave null to fall
// back to the vector icon. Add as many pages as you like to the list below.
// =============================================================================

data class OnboardingScreenData(
    val image: ImageVector,           // fallback vector icon
    val imageRes: Int? = null,        // optional drawable res (R.drawable.xxx)
    val title: String,
    val description: String
)