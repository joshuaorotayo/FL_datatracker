package com.jorotayo.fl_datatracker.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/*@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = primaryDarkRed,
    onPrimary = Color.White,
    primaryVariant = primaryLightRed,
    background = Color.DarkGray,
    onBackground = Color.LightGray,
    surface = Color.DarkGray,
    onSurface = Color.Gray.copy(alpha = 0.5f),

    *//* Other default colors to override
    *//*
)

private val LightColorPalette = lightColors(
    primary = primaryLightRed,
    onPrimary = Color.White,
    primaryVariant = primaryDarkRed,
    background = primaryLightRed,
    onBackground = Color.White,
    surface = Color.White,
    onSurface = Color.Black,

    *//* Other default colors to override
    *//*
)*/


@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = primaryDarkRed,
    onPrimary = Color.White,
    primaryVariant = primaryLightRed,
    background = primaryDarkRed,
    onBackground = Color.White,
    surface = Color.White,
    onSurface = Color.Black,
/*
 Other default colors to override*/


)

private val LightColorPalette = lightColors(
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