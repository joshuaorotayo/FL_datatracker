package com.jorotayo.fl_datatracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(66, 66, 66, 255),
    onPrimary = Color.White,
    primaryVariant = Color(37, 37, 37, 255),
    background = Color(66, 66, 66, 255),
    onBackground = Color.White,
    surface = Color.White,
    onSurface = Color.Black,
    secondary = Teal200,
    onSecondary = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = Color(192, 57, 43, 255),
    onPrimary = Color.White,
    primaryVariant = Color(243, 86, 70, 255),
    background = Color(192, 57, 43, 255),
    onBackground = Color.White,
    surface = Color.White,
    onSurface = Color.Black,
    secondary = Teal200,
    onSecondary = Color.Black,

    /* Other default colors to override
    */
)

@Composable
fun FL_DatatrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}