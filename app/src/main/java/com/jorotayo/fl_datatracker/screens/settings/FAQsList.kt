package com.jorotayo.fl_datatracker.screens.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.util.DefaultPreviews

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@DefaultPreviews
@Composable
fun PreviewFAQsList() {
    AppTheme {
        FAQsList()
    }
}

@Composable
fun FAQsList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = dimens.medium)
            .background(colors.background)
    ) {
        Text(
            modifier = Modifier
                .padding(start = dimens.small),
            text = "FAQs List",
            color = colors.secondary,
            style = typography.h2,
            textAlign = Start
        )
    }
}
