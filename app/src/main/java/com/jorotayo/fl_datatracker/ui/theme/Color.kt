package com.jorotayo.fl_datatracker.ui.theme

import androidx.compose.ui.graphics.Color

// Light Theme Colors (matching your existing design)
val md_theme_light_primary = Color(0xFF8B1A1A) // Deep burgundy/maroon from your images
val md_theme_light_secondary = Color(0xFFB71C1C) // Slightly brighter red for accents
val md_theme_light_tertiary = Color(0xFF6D1313) // Darker burgundy for depth
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_background = Color(0xFFF5F5F5) // Light gray background
val md_theme_light_surface = Color(0xFFFFFFFF) // White cards
val md_theme_light_surfaceVariant = Color(0xFFF3EFEF) // Very light gray for form background
val md_theme_light_onPrimary = Color(0xFFFFFFFF) // White text on primary
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_onBackground = Color(0xFF1C1B1F)
val md_theme_light_onSurface = Color(0xFF1C1B1F)
val md_theme_light_onSurfaceVariant = Color(0xFF49454F)
val md_theme_light_outline = Color(0xFF79747E)
val md_theme_light_outlineVariant = Color(0xFFCAC4D0)

// Container Colors
val md_theme_light_primaryContainer = Color(0xFFFFDAD6) // Light pink for badges
val md_theme_light_onPrimaryContainer = Color(0xFF410002) // Dark text on light pink
val md_theme_light_secondaryContainer = Color(0xFFFFDAD6) // Light pink for info banners
val md_theme_light_onSecondaryContainer = Color(0xFF410002)
val md_theme_light_tertiaryContainer = Color(0xFFFFD8E4)
val md_theme_light_onTertiaryContainer = Color(0xFF31111D)

// Dark Theme Colors - Improved hierarchy and burgundy tint
val md_theme_dark_primary = Color(0xFFFFB4AB) // Light coral for dark mode
val md_theme_dark_secondary = Color(0xFFE57373)
val md_theme_dark_tertiary = Color(0xFFEFB8C8)
val md_theme_dark_error = Color(0xFFFFB4AB)

// Dark Surface Hierarchy (darkest → lightest with burgundy tint)
val md_theme_dark_background = Color(0xFF1C1B1F) // Darkest - base background
val md_theme_dark_surface = Color(0xFF323038) // Slightly lighter - cards, dialogs
val md_theme_dark_surfaceVariant =
    Color(0xFF2B2730) // Lighter with burgundy tint - form backgrounds
// val md_theme_dark_surfaceVariant = Color(0xFF3A2F35) // Lighter with burgundy tint - form backgrounds

val md_theme_dark_onPrimary = Color(0xFF690005)
val md_theme_dark_onSecondary = Color(0xFF690005)
val md_theme_dark_onBackground = Color(0xFFE6E1E5)
val md_theme_dark_onSurface = Color(0xFFE6E1E5)
val md_theme_dark_onSurfaceVariant = Color(0xFFCAC4D0)
val md_theme_dark_outline = Color(0xFF938F99)
val md_theme_dark_outlineVariant = Color(0xFF49454F)

// Container Colors Dark
val md_theme_dark_primaryContainer = Color(0xFF93000A)
val md_theme_dark_onPrimaryContainer = Color(0xFFFFDAD6)
val md_theme_dark_secondaryContainer = Color(0xFF93000A)
val md_theme_dark_onSecondaryContainer = Color(0xFFFFDAD6)
val md_theme_dark_tertiaryContainer = Color(0xFF633B48)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFD8E4)

// Legacy colors for backward compatibility
val md_theme_onPrimary = Color(0xFFFFFFFF)
val md_theme_white = Color(0xFFFFFFFF)

// Custom extension colors (keep these for your custom components)
val customBodyTextColour = Color(0xFF888888)
val customHighlightLight = Color(0xFFFFFFFF)
val customHighlightDark = Color(0xFF8B1A1A)

/**
 * DARK MODE COLOR HIERARCHY EXPLANATION:
 *
 * Background (#1C1B1F) - Darkest
 * ↓ Slightly lighter
 * Surface (#2B2730) - Cards, dialogs
 * ↓ Lighter with burgundy tint
 * SurfaceVariant (#3A2F35) - Form backgrounds, emphasized areas
 *
 * The progression creates depth while maintaining the burgundy theme.
 * Each level is distinguishable but harmonious.
 *
 * Color Breakdown:
 * - Background:      RGB(28, 27, 31)   - Pure dark gray
 * - Surface:         RGB(43, 39, 48)   - Slightly lighter gray with subtle purple
 * - SurfaceVariant:  RGB(58, 47, 53)   - Lighter with burgundy/wine tint
 *
 * The burgundy tint in surfaceVariant comes from:
 * - Higher red value (58)
 * - Lower green/blue values (47, 53)
 * - Creating a warm, wine-colored undertone
 */
