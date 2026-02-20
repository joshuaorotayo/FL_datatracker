package com.jorotayo.fl_datatracker.ui.theme

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jorotayo.fl_datatracker.ui.util.SharedSettingService

/**
 * Material 3 Light Color Scheme
 * Using your existing burgundy/maroon color palette
 */
private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,

    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,

    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onPrimary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,

    error = md_theme_light_error,
    onError = md_theme_light_onPrimary,

    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,

    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,

    outline = md_theme_light_outline,
    outlineVariant = md_theme_light_outlineVariant
)

/**
 * Material 3 Dark Color Scheme
 */
private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,

    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,

    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onPrimary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,

    error = md_theme_dark_error,
    onError = md_theme_dark_onPrimary,

    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,

    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,

    outline = md_theme_dark_outline,
    outlineVariant = md_theme_dark_outlineVariant
)

/**
 * Main theme composable - Material 3 version
 */
@Composable
fun FL_DatatrackerThemeNew(
    darkTheme: Boolean = isDarkMode(),
    content: @Composable () -> Unit
) {
    val useDeviceDarkModeSettings = SharedSettingService.useDeviceDarkModeSettings.observeAsState()

    // Set app-wide dark mode
    AppCompatDelegate.setDefaultNightMode(
        when {
            useDeviceDarkModeSettings.value != false -> MODE_NIGHT_FOLLOW_SYSTEM
            darkTheme -> MODE_NIGHT_YES
            else -> MODE_NIGHT_NO
        }
    )

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = TypographyNew,
        shapes = ShapesNew,
        content = content
    )
}

/**
 * Determine if dark mode should be active
 */
@Composable
fun isDarkMode(): Boolean {
    val useDeviceDarkModeSettings = SharedSettingService.useDeviceDarkModeSettings.observeAsState()
    val systemInDarkMode = isSystemInDarkTheme()
    return (useDeviceDarkModeSettings.value != false) && systemInDarkMode
}

/**
 * LEGACY SUPPORT - Keep for backward compatibility with existing Material 2 code
 * These can be removed once all screens are migrated to Material 3
 */
@Composable
fun FL_DatatrackerTheme(
    content: @Composable () -> Unit
) {
    val useDeviceDarkModeSettings = SharedSettingService.useDeviceDarkModeSettings.observeAsState()
    val darkMode = isDarkMode()

    AppCompatDelegate.setDefaultNightMode(
        when {
            useDeviceDarkModeSettings.value != false -> MODE_NIGHT_FOLLOW_SYSTEM
            darkMode -> MODE_NIGHT_YES
            else -> MODE_NIGHT_NO
        }
    )

    val colors = if (!darkMode) {
        androidx.compose.material.lightColors(
            primary = md_theme_light_primary,
            onPrimary = md_theme_onPrimary,
            secondary = md_theme_light_secondary,
            onSecondary = md_theme_white,
            error = md_theme_light_error,
            onError = md_theme_white,
            background = md_theme_light_background,
            onBackground = md_theme_light_onBackground,
            surface = md_theme_light_surface,
            onSurface = md_theme_light_onSurface
        )
    } else {
        androidx.compose.material.darkColors(
            primary = md_theme_dark_primary,
            onPrimary = md_theme_onPrimary,
            secondary = md_theme_dark_secondary,
            onSecondary = md_theme_white,
            error = md_theme_dark_error,
            onError = md_theme_white,
            background = md_theme_dark_background,
            onBackground = md_theme_white,
            surface = md_theme_dark_background,
            onSurface = md_theme_white
        )
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(colors.background)
    systemUiController.setStatusBarColor(colors.background)

    androidx.compose.material.MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

// Legacy extension properties for backward compatibility
val androidx.compose.material.Colors.highLightColours: Color
    get() = if (isLight) Color.White else md_theme_light_primary

val androidx.compose.material.Colors.headingTextColour: Color
    get() = if (isLight) md_theme_light_primary else md_theme_dark_primary

val androidx.compose.material.Colors.subtitleTextColour: Color
    get() = if (isLight) Color.Black else Color.White

val androidx.compose.material.Colors.bodyTextColour: Color
    get() = customBodyTextColour

val darkSurfaceHeadingColour: Color
    get() = Color.White

val lightSurfaceHeadingColour: Color
    get() = Color.Black