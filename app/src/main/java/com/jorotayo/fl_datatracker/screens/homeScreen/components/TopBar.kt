package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.layout.*
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

}

@Composable
fun TopBar(
    toggleSearchBar: () -> Unit,
    showSettingsView: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        Text(
            text = "FL DataTracker",
            modifier = Modifier
                .weight(10f),
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h4.also { FontWeight.Bold },
            textAlign = TextAlign.Start
        )
        Row(
            modifier = Modifier
                .wrapContentSize()
        ) {
            SimpleIconButton(
                modifier = Modifier,
                icon = Icons.Default.Settings,
                tint = MaterialTheme.colors.primary,
                contentDescription = "Settings Icon to edit in App Settings",
                onClick = showSettingsView
            )
            SimpleIconButton(
                modifier = Modifier,
                icon = Icons.Default.Search,
                tint = MaterialTheme.colors.primary,
                contentDescription = "Search Icon to toggle Search Bar and search for Data",
                onClick = toggleSearchBar
            )
        }
    }
}