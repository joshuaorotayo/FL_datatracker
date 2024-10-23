package com.jorotayo.fl_datatracker.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview


@Preview(name = "LightMode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "DarkMdoe", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(
    name = "SmallLightMode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    heightDp = 600,
    widthDp = 360
)

annotation class DefaultPreviews 