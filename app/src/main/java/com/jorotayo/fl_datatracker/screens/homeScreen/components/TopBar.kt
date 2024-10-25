package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.util.Dimen.large
import com.jorotayo.fl_datatracker.util.Dimen.one
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall

@DefaultPreviews
@Composable
private fun PreviewTopBar() {
    FL_DatatrackerTheme {
        TopBar(
            toggleSearchBar = {},
        )
    }
}

@Composable
fun TopBar(toggleSearchBar: () -> Unit) {
    val isSearchExpanded by remember { mutableStateOf(false) }
    Column {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(small),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically,
        ) {
            AnimatedVisibility(!isSearchExpanded) {
                Text(
                    modifier =
                    Modifier
                        .wrapContentWidth()
                        .padding(end = large),
                    text = "FL DataTracker",
                    color = MaterialTheme.colors.primary,
                    style = typography.h1,
                    textAlign = TextAlign.Start,
                )
            }
            androidx.compose.material.Surface(
                modifier =
                Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(xSmall),
                color = MaterialTheme.colors.surface,
                elevation = if (isDarkMode()) one else xxSmall,
            ) {
                Row(
                    modifier =
                    Modifier
                        .padding(start = xxSmall),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Search", color = MaterialTheme.colors.onSurface)
                    SimpleIconButton(
                        modifier =
                        Modifier
                            .size(xSmall + xxSmall),
                        icon = Icons.Default.Search,
                        tint = MaterialTheme.colors.primary,
                        contentDescription = "Search Icon to toggle Search Bar and search for Data",
                        onClick = toggleSearchBar,
                    )
                }
            }
        }
    }
}
