package com.jorotayo.fl_datatracker.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = primaryDarkRed,
    onPrimary = Color.White,
    primaryVariant = primaryLightRed,
    background = Color(18, 20, 24, 255),
    surface = Color(27, 31, 36, 255),
    onBackground = Color(0, 0, 0, 255),
    onSurface = Color.White,
)

private val LightColorPalette = lightColors(
    primary = primaryLightRed,
    onPrimary = Color.White,
    primaryVariant = primaryDarkRed,
    background = Color(248, 248, 248, 255), //ghost white
    onBackground = Color(241, 241, 241, 250),//light red
    secondary = Color(237, 200, 182, 125), //darker light red for panels/sections
    surface = Color(255, 255, 255, 255), //pure white
    onSurface = Color.Black, //text colour for headings, Gray will be subheadings

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