package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode

@DefaultPreviews
@Composable
fun PreviewMinimalBooleanField() {
    AppTheme {
        minimalBooleanField(rowHeader = "Minimal Boolean Field", isError = false)
    }
}

@Composable
fun minimalBooleanField(
    rowHeader: String,
    isError: Boolean,
): String {

    var cardElevation by remember { mutableStateOf(12.dp) }
    var checked by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .wrapContentSize()
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .wrapContentHeight()
                .padding(dimens.xxSmall)
                .onFocusChanged {
                    cardElevation =
                        if (it.isFocused) 32.dp else 12.dp
                },
            shape = RoundedCornerShape(dimens.xSmall),
            backgroundColor = if (!isDarkMode() && cardElevation == dimens.medium) MaterialTheme.colors.surface.copy(
                alpha = 0.5f
            ) else MaterialTheme.colors.surface,
            elevation = if (isDarkMode()) cardElevation else dimens.zero
        ) {
            Column(
                modifier = Modifier
                    .padding(dimens.xxSmall),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = rowHeader,
                    style = MaterialTheme.typography.body1,
                    color = Color.DarkGray
                )
                Switch(
                    modifier = Modifier.fillMaxWidth(),
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colors.primary,
                        checkedTrackColor = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                        uncheckedThumbColor = MaterialTheme.colors.onSurface,
                        uncheckedTrackColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                    )
                )
            }
        }
    }
    return checked.toString()
}