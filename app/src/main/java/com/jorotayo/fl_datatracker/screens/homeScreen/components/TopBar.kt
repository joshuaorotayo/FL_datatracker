package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode

@DefaultPreviews
@Composable
fun PreviewTopBar() {
    AppTheme {
        TopBar(
            toggleSearchBar = {},
        )
    }
}

@Composable
fun TopBar(
    toggleSearchBar: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimens.small),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .padding(end = dimens.large),
            text = "FL DataTracker",
            color = MaterialTheme.colors.primary,
            style = typography.h1,
            textAlign = TextAlign.Start
        )
        androidx.compose.material.Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(dimens.xSmall),
            color = MaterialTheme.colors.surface,
            elevation = if (isDarkMode()) dimens.xxSmall else dimens.zero
        ) {
            Row(
                modifier = Modifier
                    .padding(start = dimens.xxSmall),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Search", color = MaterialTheme.colors.onSurface)
                SimpleIconButton(
                    modifier = Modifier
                        .size(dimens.xSmall + dimens.xxSmall),
                    icon = Icons.Default.Search,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = "Search Icon to toggle Search Bar and search for Data",
                    onClick = toggleSearchBar
                )
            }
        }

    }
}
