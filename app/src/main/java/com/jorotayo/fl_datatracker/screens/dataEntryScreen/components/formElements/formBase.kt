package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxxSmall
import com.jorotayo.fl_datatracker.util.Dimen.zero

@Composable
fun DefaultContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text("Default Empty Heading", color = MaterialTheme.colors.onSurface)
        Text("Default Empty content", color = MaterialTheme.colors.secondary)
    }
}

@Composable
fun FormBase(
    content: @Composable () -> Unit = { DefaultContent() }
) {
    Card(
        modifier = Modifier
            .padding(start = xSmall, top = xSmall, end = xSmall, bottom = zero)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.surface),
        shape = RoundedCornerShape(xSmall),
        elevation = xxxSmall
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(xSmall)
        ) {
            content()
        }
    }
}

@DefaultDualPreview
@Composable
fun FormBasePreview() {
    FL_DatatrackerTheme {
        FormBase()
    }
}
