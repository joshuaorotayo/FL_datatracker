package com.jorotayo.fl_datatracker.screens.homeScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.xSmall

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewTopBar() {
    FL_DatatrackerTheme {
        TopBar(
            toggleSearchBar = {},
            settingsNavigate = {},
        )
    }
}

@Composable
fun TopBar(
    toggleSearchBar: () -> Unit,
    settingsNavigate: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(xSmall),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        Text(
            text = "FL DataTracker",
            modifier = Modifier.wrapContentWidth(),
            color = MaterialTheme.colors.primary,
            style = typography.h1,
            textAlign = TextAlign.Start
        )
        SimpleIconButton(
            modifier = Modifier
                .weight(1f),
            icon = Icons.Default.Settings,
            tint = MaterialTheme.colors.primary,
            contentDescription = "Settings Icon to edit in App Settings",
            onClick = settingsNavigate
        )
        SimpleIconButton(
            modifier = Modifier
                .weight(1f),
            icon = Icons.Default.Search,
            tint = MaterialTheme.colors.primary,
            contentDescription = "Search Icon to toggle Search Bar and search for Data",
            onClick = toggleSearchBar
        )
    }
}