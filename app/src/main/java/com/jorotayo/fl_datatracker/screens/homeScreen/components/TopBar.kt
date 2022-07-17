package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel

@Preview(showBackground = true)
@Composable
fun PreviewTopBar() {
    TopBar(
        viewModel = hiltViewModel(),
        isSearchVisible = false
    )
}

@Composable
fun TopBar(
    viewModel: HomeScreenViewModel,
    isSearchVisible: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        SimpleIconButton(
            modifier = Modifier,
            icon = Icons.Default.Checklist,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = "Menu Icon for editing the Data Fields"
        ) {
            //On Click method
        }
        Text(
            text = "FL DataTracker",
            modifier = Modifier.fillMaxWidth(0.8f),
            color = MaterialTheme.colors.onPrimary
        )
        SimpleIconButton(
            modifier = Modifier,
            icon = Icons.Default.Search,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = "Search Icon to toggle Search Bar and search for Data"
        ) {
            viewModel.onEvent(HomeScreenEvent.ToggleSearchBar(!isSearchVisible))
        }
    }
}