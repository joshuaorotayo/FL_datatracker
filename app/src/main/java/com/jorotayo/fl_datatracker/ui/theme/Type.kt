package com.jorotayo.fl_datatracker.ui.theme

import androidx.compose.material.Typography
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
val Typography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        letterSpacing = 1.sp
    ),

    h2 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.5.sp
    ),

    subtitle1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),

    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),

    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.25.sp
    ),

    button = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp
    ),

    caption = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 20.sp
    )
)