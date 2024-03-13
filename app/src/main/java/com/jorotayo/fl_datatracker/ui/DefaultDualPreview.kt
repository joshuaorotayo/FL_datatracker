package com.jorotayo.fl_datatracker.ui

import android.content.res.Configuration
import android.os.Build
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode",
    apiLevel = Build.VERSION_CODES.R
)
@Preview(
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode",
    apiLevel = Build.VERSION_CODES.R
)

annotation class DefaultDualPreview
