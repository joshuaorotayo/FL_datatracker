package com.jorotayo.fl_datatracker.ui.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Dimensions - Modern 4dp Grid Spacing System
 *
 * A consistent spacing scale based on 4dp increments for visual rhythm and hierarchy.
 * Use these values throughout the app for margins, padding, gaps, and sizing.
 *
 * PRINCIPLE: All spacing should be a multiple of 4dp for consistency.
 *
 * MIGRATION FROM OLD DIMEN:
 * - xxxSmall (4dp) → spacingXXSmall
 * - xxSmall (8dp) → spacingXSmall
 * - xSmall (12dp) → spacingSmall
 * - small (16dp) → spacingMedium
 * - regular (28dp) → spacingXLarge
 * - medium (32dp) → spacingXXLarge
 */

object Dimensions {

    // ============================================================================
    // SPACING - Core spacing values (4dp grid)
    // ============================================================================

    /**
     * 0dp - No spacing
     * Use for: Removing default spacing
     */
    val spacingNone: Dp = 0.dp

    /**
     * 2dp - Minimal spacing (use sparingly, prefer 4dp)
     * Use for: Extremely tight spacing, borders
     */
    val spacingXXXSmall: Dp = 2.dp

    /**
     * 4dp - Extra tight spacing
     * Use for: Label directly above input field, very closely related items
     * Example: "Email" label → TextField (4dp gap)
     */
    val spacingXXSmall: Dp = 4.dp

    /**
     * 8dp - Tight spacing
     * Use for: Icon next to text, closely related items in a row
     * Example: 🖊️ (icon) → "Edit" (text) with 8dp between
     */
    val spacingXSmall: Dp = 8.dp

    /**
     * 12dp - Small spacing
     * Use for: Between buttons in a row, related card elements
     * Example: [Cancel] (12dp) [Save] buttons
     */
    val spacingSmall: Dp = 12.dp

    /**
     * 16dp - Medium spacing (MOST COMMON)
     * Use for: Screen edges, card padding, between major sections
     * Example: Screen horizontal padding, card internal padding
     */
    val spacingMedium: Dp = 16.dp

    /**
     * 20dp - Medium-Large spacing
     * Use for: Between form fields in a card
     * Example: Gap between TextField → TextField in a form
     */
    val spacingMediumLarge: Dp = 20.dp

    /**
     * 24dp - Large spacing
     * Use for: Between distinct sections, major UI separations
     * Example: Header section → Content section
     */
    val spacingLarge: Dp = 24.dp

    /**
     * 28dp - Extra Large spacing
     * Use for: Large vertical gaps, screen sections
     */
    val spacingXLarge: Dp = 28.dp

    /**
     * 32dp - Extra Extra Large spacing
     * Use for: Major section separation
     */
    val spacingXXLarge: Dp = 32.dp

    /**
     * 48dp - Huge spacing
     * Use for: Large vertical separations, bottom padding before FAB
     */
    val spacingHuge: Dp = 48.dp

    /**
     * 64dp - Extra Huge spacing
     * Use for: Very large separations, empty state padding
     */
    val spacingXHuge: Dp = 64.dp

    /**
     * 80dp - Massive spacing
     * Use for: Bottom padding to clear FAB + nav bar
     */
    val spacingMassive: Dp = 80.dp

    // ============================================================================
    // ICON SIZES
    // ============================================================================

    /**
     * 16dp - Small icons
     * Use for: Inline icons in text, small decorative icons
     */
    val iconXSmall: Dp = 16.dp

    /**
     * 18dp - Small-Medium icons
     * Use for: Button icons, checkmarks in chips
     */
    val iconSmall: Dp = 18.dp

    /**
     * 20dp - Medium icons (MOST COMMON)
     * Use for: Text field leading icons, card icons
     */
    val iconMedium: Dp = 20.dp

    /**
     * 24dp - Standard icons
     * Use for: App bar icons, list item icons, primary actions
     */
    val iconStandard: Dp = 24.dp

    /**
     * 32dp - Large icons
     * Use for: Feature icons, prominent actions
     */
    val iconLarge: Dp = 32.dp

    /**
     * 48dp - Extra Large icons
     * Use for: FAB icons, hero icons
     */
    val iconXLarge: Dp = 48.dp

    // ============================================================================
    // COMPONENT SIZES
    // ============================================================================

    /**
     * 32dp - Small button/component
     * Use for: Small icon buttons, compact UI elements
     */
    val componentSmall: Dp = 32.dp

    /**
     * 40dp - Medium button/component
     * Use for: Icon buttons in dense layouts
     */
    val componentMedium: Dp = 40.dp

    /**
     * 48dp - Standard button/component (MINIMUM TOUCH TARGET)
     * Use for: Standard buttons, icon buttons, list items
     */
    val componentStandard: Dp = 48.dp

    /**
     * 50dp - Button with text
     * Use for: Primary/Secondary buttons with text
     */
    val componentButton: Dp = 50.dp

    /**
     * 56dp - Large button/component
     * Use for: Text fields, large buttons, FAB
     */
    val componentLarge: Dp = 56.dp

    /**
     * 64dp - Extra Large component
     * Use for: Bottom sheets, large cards
     */
    val componentXLarge: Dp = 64.dp

    // ============================================================================
    // BORDER & STROKE WIDTHS
    // ============================================================================

    /**
     * 1dp - Thin border
     * Use for: Outlined buttons, text fields, dividers
     */
    val borderThin: Dp = 1.dp

    /**
     * 2dp - Medium border
     * Use for: Focus states, emphasis borders
     */
    val borderMedium: Dp = 2.dp

    /**
     * 4dp - Thick border
     * Use for: Strong emphasis, decorative borders
     */
    val borderThick: Dp = 4.dp

    // ============================================================================
    // ELEVATION (SHADOWS)
    // ============================================================================

    /**
     * 0dp - No elevation
     * Use for: Flat surfaces, inactive cards
     */
    val elevationNone: Dp = 0.dp

    /**
     * 1dp - Subtle elevation
     * Use for: Active cards, raised surfaces
     */
    val elevationXSmall: Dp = 1.dp

    /**
     * 2dp - Small elevation
     * Use for: Cards, floating elements
     */
    val elevationSmall: Dp = 2.dp

    /**
     * 4dp - Medium elevation
     * Use for: App bars, floating panels
     */
    val elevationMedium: Dp = 4.dp

    /**
     * 6dp - Large elevation (Material 3 FAB default)
     * Use for: FAB, prominent floating elements
     */
    val elevationLarge: Dp = 6.dp

    /**
     * 8dp - Extra Large elevation
     * Use for: Dialogs, modal sheets
     */
    val elevationXLarge: Dp = 8.dp

    // ============================================================================
    // SPECIAL SIZES
    // ============================================================================

    /**
     * 48dp - Status bar padding
     * Use for: Top padding to clear status bar
     */
    val statusBarPadding: Dp = 48.dp

    /**
     * 60dp - Bottom bar padding
     * Use for: Bottom padding to clear navigation bar
     */
    val bottomBarPadding: Dp = 60.dp

    /**
     * 120dp - Minimum text field height for multi-line
     * Use for: Long text fields, description fields
     */
    val textFieldMultiLineMin: Dp = 120.dp

    // ============================================================================
    // CHARACTER LIMITS (Constants)
    // ============================================================================

    /**
     * 15 characters - Option text limit
     * Use for: Boolean option labels, chip labels
     */
    const val optionsMaxChars: Int = 15

    /**
     * 50 characters - Short text field limit
     * Use for: Titles, names, short descriptions
     */
    const val shortTextMaxChars: Int = 50

    /**
     * 200 characters - Long text field limit
     * Use for: Descriptions, notes, comments
     */
    const val longTextMaxChars: Int = 200

    /**
     * 500 characters - Extra long text limit
     * Use for: Detailed descriptions, long-form content
     */
    const val extraLongTextMaxChars: Int = 500

    // ============================================================================
    // PERCENTAGE WEIGHTS (Constants)
    // ============================================================================

    const val weightTen: Float = 0.1f
    const val weightTwenty: Float = 0.2f
    const val weightThirty: Float = 0.3f
    const val weightForty: Float = 0.4f
    const val weightFifty: Float = 0.5f
    const val weightSixty: Float = 0.6f
    const val weightSeventy: Float = 0.7f
    const val weightEighty: Float = 0.8f
    const val weightNinety: Float = 0.9f
}

/**
 * USAGE EXAMPLES:
 *
 * // Screen padding
 * Column(modifier = Modifier.padding(horizontal = Dimensions.spacingMedium))
 *
 * // Between form fields
 * Column(verticalArrangement = Arrangement.spacedBy(Dimensions.spacingMediumLarge))
 *
 * // Label to input
 * Column(verticalArrangement = Arrangement.spacedBy(Dimensions.spacingXXSmall))
 *
 * // Icon size
 * Icon(modifier = Modifier.size(Dimensions.iconMedium))
 *
 * // Button height
 * Button(modifier = Modifier.height(Dimensions.componentButton))
 *
 * // Card elevation
 * Card(elevation = CardDefaults.cardElevation(defaultElevation = Dimensions.elevationSmall))
 *
 * // Character limit
 * if (text.length <= Dimensions.shortTextMaxChars) { }
 *
 * // Layout weight
 * Box(modifier = Modifier.weight(Dimensions.weightFifty))
 */

/**
 * MIGRATION GUIDE FROM OLD DIMEN:
 *
 * OLD                    →  NEW
 * ─────────────────────────────────────────────────────
 * Dimen.zero            →  Dimensions.spacingNone
 * Dimen.one             →  Dimensions.borderThin
 * Dimen.xxxxSmall (2dp) →  Dimensions.spacingXXXSmall
 * Dimen.xxxSmall (4dp)  →  Dimensions.spacingXXSmall
 * Dimen.xxSmall (8dp)   →  Dimensions.spacingXSmall
 * Dimen.xSmall (12dp)   →  Dimensions.spacingSmall
 * Dimen.small (16dp)    →  Dimensions.spacingMedium
 * Dimen.regular (28dp)  →  Dimensions.spacingXLarge
 * Dimen.medium (32dp)   →  Dimensions.spacingXXLarge
 * Dimen.large (48dp)    →  Dimensions.spacingHuge / componentStandard
 * Dimen.xLarge (64dp)   →  Dimensions.spacingXHuge
 * Dimen.xxLarge (80dp)  →  Dimensions.spacingMassive
 * Dimen.iconSize (24dp) →  Dimensions.iconStandard
 *
 * CHARACTER LIMITS:
 * Dimen.optionsMaxChars →  Dimensions.optionsMaxChars
 * Dimen.shortTextMaxChars → Dimensions.shortTextMaxChars
 *
 * PERCENTAGES:
 * Dimen.tenPercent      →  Dimensions.weightTen
 * Dimen.fiftyPercent    →  Dimensions.weightFifty
 * etc.
 */
