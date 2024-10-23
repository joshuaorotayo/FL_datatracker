package com.jorotayo.fl_datatracker.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppDimensions(
    val zero: Dp,
    val one: Dp,
    val xxxxSmall: Dp,
    val xxxSmall: Dp,
    val xxSmall: Dp,
    val xSmall: Dp,
    val small: Dp,
    val regular: Dp,
    val icon: Dp,
    val medium: Dp,
    val large: Dp,
    val xLarge: Dp,
    val xxLarge: Dp,
    val xxxLarge: Dp,
    val tenPercent: Float,
    val twentyPercent: Float,
    val thirtyPercent: Float,
    val fortyPercent: Float,
    val fiftyPercent: Float,
    val sixtyPercent: Float,
    val seventyPercent: Float,
    val eightyPercent: Float,
    val ninetyPercent: Float,
    val statusBarPadding: Dp,
    val optionsMaxChars: Int,
    val bottomBarPadding: Dp
)

val LocalAppDimensions = staticCompositionLocalOf {
    AppDimensions(
        zero = 0.dp,
        one = 0.dp,
        xxxxSmall = 0.dp,
        xxxSmall = 0.dp,
        xxSmall = 0.dp,
        xSmall = 0.dp,
        small = 0.dp,
        regular = 0.dp,
        icon = 0.dp,
        medium = 0.dp,
        large = 0.dp,
        xLarge = 0.dp,
        xxLarge = 0.dp,
        xxxLarge = 0.dp,
        tenPercent = 0.0f,
        twentyPercent = 0.0f,
        thirtyPercent = 0.0f,
        fortyPercent = 0.0f,
        fiftyPercent = 0.0f,
        sixtyPercent = 0.0f,
        seventyPercent = 0.0f,
        eightyPercent = 0.0f,
        ninetyPercent = 0.0f,
        statusBarPadding = 0.dp,
        optionsMaxChars = 0,
        bottomBarPadding = 0.dp
    )
}

val extendedDimensions = AppDimensions(
    zero = 0.dp,
    one = 1.dp,
    xxxxSmall = 2.dp,
    xxxSmall = 4.dp,
    xxSmall = 8.dp,
    xSmall = 12.dp,
    small = 16.dp,
    regular = 20.dp,
    icon = 24.dp,
    medium = 32.dp,
    large = 48.dp,
    xLarge = 64.dp,
    xxLarge = 80.dp,
    xxxLarge = 96.dp,
    tenPercent = 0.1f,
    twentyPercent = 0.2f,
    thirtyPercent = 0.3f,
    fortyPercent = 0.4f,
    fiftyPercent = 0.5f,
    sixtyPercent = 0.6f,
    seventyPercent = 0.7f,
    eightyPercent = 0.8f,
    ninetyPercent = 0.9f,
    statusBarPadding = 48.dp,
    optionsMaxChars = 15,
    bottomBarPadding = 60.dp
)
