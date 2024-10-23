package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode

@Composable
fun DefaultContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text("Default Empty Heading", color = colors.onSurface)
        Text("Default Empty content", color = colors.secondary)
    }
}

@Composable
fun FormBase(
    content: @Composable () -> Unit = { DefaultContent() }
) {
    Card(
        modifier = Modifier
            .padding(AppTheme.dimens.small)
            .fillMaxWidth(),
        shape = RoundedCornerShape(AppTheme.dimens.small),
        backgroundColor = colors.surface,
        elevation = if (isDarkMode()) AppTheme.dimens.xSmall else AppTheme.dimens.zero
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(AppTheme.dimens.xSmall)
        ) {
            content()
        }
    }
}

@DefaultPreviews
@Composable
fun FormBasePreview() {
    AppTheme {
        FormBase()
    }
}
