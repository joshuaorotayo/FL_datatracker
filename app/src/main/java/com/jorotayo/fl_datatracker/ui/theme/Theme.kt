package com.jorotayo.fl_datatracker.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jorotayo.fl_datatracker.util.SharedSettingService

/*private val lightColours = lightColors(
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
)*/

/*private val darkColours = darkColors(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_onPrimary,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_white,
    error = md_theme_dark_error,
    onError = md_theme_white,
    background = md_theme_dark_background,
    onBackground = md_theme_white,
    surface = md_theme_dark_background, // elevation xxAppTheme.dimens.xSmall
    onSurface = md_theme_white,
)*/

val Colors.highLightColours: Color
    get() = if (isLight) Color.White else Color.Black
val Colors.headingTextColour: Color
    get() = if (isLight) black else white

val Colors.subtitleTextColour: Color
    get() = if (isLight) Color.Black else Color.White

val Colors.bodyTextColour: Color
    get() = Color(color = 0xFF888888)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val useDeviceDarkModeSettings = SharedSettingService.useDeviceDarkModeSettings.observeAsState()

    val darkMode = isDarkMode()

    AppCompatDelegate.setDefaultNightMode(
        when {
            useDeviceDarkModeSettings.value!! -> MODE_NIGHT_FOLLOW_SYSTEM
            darkMode -> MODE_NIGHT_YES
            else -> MODE_NIGHT_NO
        }
    )

    val colors = if (!darkMode) {
        lightColours
    } else {
        darkColours
    }

    val systemUiController = rememberSystemUiController()

    systemUiController.setNavigationBarColor(colors.background)
    systemUiController.setStatusBarColor(colors.background)

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides extendedTypography,
        LocalAppDimensions provides extendedDimensions
    ) {
        androidx.compose.material3.MaterialTheme(
            content = content
        )
    }

}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current
    val dimens: AppDimensions
        @Composable
        get() = LocalAppDimensions.current
}

@Composable
fun isDarkMode(): Boolean {
    val useDeviceDarkModeSettings = SharedSettingService.useDeviceDarkModeSettings.observeAsState()

    val systemInDarkMode = isSystemInDarkTheme()

    return (useDeviceDarkModeSettings.value!! && systemInDarkMode)
}
