package com.jorotayo.fl_datatracker.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


val white = Color(color = 0xFFFFFFFF)
val black = Color(color = 0xFF000000)
val gray = Color(color = 0xFF888888)
val primaryLightRed = Color(color = 0xFFC0392B)
val primaryDarkRed = Color(color = 0xFF890001)
val whiteRed = Color(color = 0xFFFFEBEE)
val whiteSecondaryRed = Color(color = 0xFFFFCDD2)

val darkBackground = Color(color = 0xFF1E1F22)
val darkRed = Color(color = 0xFF332C2C)
val darkSecondaryRed = Color(color = 0xFF3F3333)


/*val primaryDarkRed = Color(color = 0xFF890001)
val darkSurfaceHeadingColour = Color(0xFFFFFFFF)
val lightSurfaceHeadingColour = Color(0xFFB02D21)
val md_theme_light_primary = Color(color = 0xFF890001)
val md_theme_light_variant = Color(color = 0xFFEF5350)
val md_theme_onPrimary = Color(color = 0xFFFFFFFF)
val md_theme_light_secondary = Color(color = 0xFF393C41)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_background = Color(color = 0xFFF0F0F0) //off white
val md_theme_light_onBackground = Color(color = 0xFF1E1F22)
val md_theme_light_surface = Color(color = 0xFFFFFFFF)
val md_theme_light_onSurface = Color(color = 0xFF1E1F22)

val md_theme_dark_primary = Color(color = 0xFFB02D21)
val md_theme_dark_error = Color(color = 0xFFC0392B)
val md_theme_dark_background = Color(color = 0xFF1E1F22)
val md_theme_dark_secondary = Color(color = 0xFF393C41)
val seed = Color(color = 0xFFC0392B)*/


@Immutable
data class AppColors(
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val secondarySurface: Color,
    val onSecondarySurface: Color,
    val regularSurface: Color,
    val onRegularSurface: Color,
    val actionSurface: Color,
    val onActionSurface: Color,
    val highlightSurface: Color,
    val onHighlightSurface: Color
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        secondarySurface = Color.Unspecified,
        onSecondarySurface = Color.Unspecified,
        regularSurface = Color.Unspecified,
        onRegularSurface = Color.Unspecified,
        actionSurface = Color.Unspecified,
        onActionSurface = Color.Unspecified,
        highlightSurface = Color.Unspecified,
        onHighlightSurface = Color.Unspecified
    )
}

val lightColours = AppColors(
    background = white,
    onBackground = primaryLightRed,
    surface = whiteRed,
    onSurface = black,
    secondarySurface = whiteSecondaryRed,
    onSecondarySurface = Color.Gray,
    regularSurface = whiteRed,
    onRegularSurface = black,
    actionSurface = primaryLightRed,
    onActionSurface = white,
    highlightSurface = primaryLightRed,
    onHighlightSurface = white
)

val darkColours = AppColors(
    background = darkBackground,
    onBackground = primaryDarkRed,
    surface = darkRed,
    onSurface = white,
    secondarySurface = darkSecondaryRed,
    onSecondarySurface = Color.LightGray,
    regularSurface = darkRed,
    onRegularSurface = white,
    actionSurface = primaryDarkRed,
    onActionSurface = black,
    highlightSurface = primaryDarkRed,
    onHighlightSurface = black
)