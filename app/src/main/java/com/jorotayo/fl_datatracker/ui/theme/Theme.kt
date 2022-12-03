package com.jorotayo.fl_datatracker.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@SuppressLint("ConflictingOnColor")
private val DarkColorPaletteOld = darkColors(
    primary = primaryLightRed,
    onPrimary = Color.White,
    primaryVariant = primaryDarkRed,
    background = Color(42, 42, 44, 255),
    onBackground = Color(32, 32, 34, 255),
    surface = Color(32, 32, 34, 255),
    onSurface = Color.White,
/*
 Other default colors to override*/
)

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = primaryLightRed,
    onPrimary = Color.White,
    primaryVariant = primaryDarkRed,
    background = Color(18, 20, 24, 255),
    surface = Color(27, 31, 36, 255),
    onBackground = Color(0, 0, 0, 255),
    onSurface = Color.White,
/*
 Other default colors to override*/
)

private val LightColorPalette = lightColors(
    primary = primaryLightRed,
    onPrimary = Color.White,
    primaryVariant = primaryDarkRed,
    background = Color(248, 248, 248, 255), //ghost white
    onBackground = Color(245, 236, 235, 250),//light red
    secondary = Color(237, 200, 182, 125), //darker light red for panels/sections
    surface = Color(255, 255, 255, 255), //pure white
    onSurface = Color.Black, //text colour for headings, Gray will be subheadings
/*
 Other default colors to override
    */
)

private val LightColorPaletteOld = lightColors(
    primary = primaryLightRed,
    onPrimary = Color.White,
    primaryVariant = primaryDarkRed,
    background = Color(245, 236, 235, 250), //light red for background
    onBackground = Color.White,
    surface = Color(237, 200, 182, 125), //darker light red for panels/sections
    onSurface = Color.Black, //text colour for headings, Gray will be subheadings
/*
 Other default colors to override
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