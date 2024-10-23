package com.jorotayo.fl_datatracker.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/*val Martel = FontFamily(
    Font(R.font.martel_regular),
    Font(R.font.martel_semi_bold, FontWeight.SemiBold),
    Font(R.font.martel_bold, FontWeight.Bold),
    Font(R.font.martel_light, FontWeight.Light),
    Font(R.font.martel_extra_bold, FontWeight.ExtraBold),
    Font(R.font.martel_extra_light, FontWeight.ExtraLight),
    Font(R.font.martel_black, FontWeight.Black)
)*/

// Set of Material typography styles to start with
//val Typography = Typography(
//    h1 = TextStyle(
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 28.sp,
//        letterSpacing = 1.sp
//    ),
//
//    h2 = TextStyle(
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 20.sp,
//        letterSpacing = 0.5.sp
//    ),
//
//    h3 = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        letterSpacing = 0.25.sp
//    ),
//
//    subtitle1 = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontSize = 14.sp,
//        letterSpacing = 0.sp
//    ),
//
//    body1 = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontSize = 14.sp,
//        letterSpacing = 0.sp
//    ),
//
//    body2 = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontSize = 10.sp,
//        letterSpacing = 0.sp
//    ),
//
//    button = TextStyle(
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 18.sp
//    )
//)


@Immutable
data class AppTypography(
    val headline: TextStyle,
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val body: TextStyle,
    val bodySmall: TextStyle,
    val label: TextStyle
)

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        headline = TextStyle.Default,
        titleLarge = TextStyle.Default,
        titleMedium = TextStyle.Default,
        titleSmall = TextStyle.Default,
        body = TextStyle.Default,
        bodySmall = TextStyle.Default,
        label = TextStyle.Default
    )
}

val extendedTypography = AppTypography(
    headline = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Normal
    ),
    titleLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal
    ),
    titleMedium = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    titleSmall = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    body = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    label = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.Light
    )
)

