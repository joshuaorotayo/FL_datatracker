package com.jorotayo.fl_datatracker.ui.theme

/*
@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = primaryLightRed,
    onPrimary = Color.Black,
    background = Color(0, 0, 0, 255), //white
    onBackground = Color(37, 9, 2, 255),
    primaryVariant = Color(100, 13, 20, 255),
    secondary = Color(237, 200, 182, 125), //darker light red for panels/sections
    surface = Color(26, 25, 25, 255), //grey text colour for headings, Gray will be subheadings
    onSurface = Color(255, 255, 255, 255), //pure white
)

*/
/*private val LightColorPalette = lightColors(
    primary = primaryLightRed,
    onPrimary = Color.White,
    primaryVariant = primaryDarkRed,
    background = Color(248, 248, 248, 255), //ghost white
    onBackground = Color(255, 44, 44, 255),//light red
    secondary = Color(237, 200, 182, 125), //darker light red for panels/sections
    surface = Color(255, 255, 255, 255), //pure white
    onSurface = Color.Black, //text colour for headings, Gray will be subheadings
)*//*


private val LightColorPalette = lightColors(
    primary = primaryLightRed,
    onPrimary = Color.White,
    primaryVariant = primaryDarkRed,
    background = Color(255, 255, 255, 255), //white
    onBackground = Color(0, 0, 0, 255),//grey
    secondary = Color(237, 200, 182, 125), //darker light red for panels/sections
    surface = Color(255, 255, 255, 255), //pure white
    onSurface = Color(0, 0, 0, 255) //grey text colour for headings, Gray will be subheadings
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
}*/


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val lightColours = lightColors(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
)


private val darkColours = darkColors(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
)

@Composable
fun FL_DatatrackerTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        lightColours
    } else {
        darkColours
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}


