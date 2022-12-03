package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun PreviewTopBar() {
    TopBar(
        toggleSearchBar = {},
        showSettingsView = {},
        settingsNavigate = {},
    )
}

@Composable
fun TopBar(
    toggleSearchBar: () -> Unit,
    showSettingsView: () -> Unit,
    settingsNavigate: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        SimpleIconButton(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    settingsNavigate()
                },
            icon = Icons.Default.Settings,
            tint = MaterialTheme.colors.surface,
            contentDescription = "Settings Icon to edit in App Settings",
            onClick = showSettingsView
        )
        Text(
            text = "FL DataTracker",
            modifier = Modifier
                .weight(10f),
            color = MaterialTheme.colors.surface,
            style = MaterialTheme.typography.h5.also { FontWeight.Bold },
            textAlign = TextAlign.Center
        )
        SimpleIconButton(
            modifier = Modifier
                .weight(1f),
            icon = Icons.Default.Search,
            tint = MaterialTheme.colors.surface,
            contentDescription = "Search Icon to toggle Search Bar and search for Data",
            onClick = toggleSearchBar
        )
    }

}