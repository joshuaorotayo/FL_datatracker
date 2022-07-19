package com.jorotayo.fl_datatracker.screens.homeScreen.components

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel

@Preview(showBackground = true)
@Composable
fun PreviewTopBar() {
    TopBar(
        viewModel = hiltViewModel(),
        context = LocalContext.current
    )
}

@Composable
fun TopBar(
    viewModel: HomeScreenViewModel,
    context: Context
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        SimpleIconButton(
            modifier = Modifier
                .weight(1f),
            icon = Icons.Default.Checklist,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = "Menu Icon for editing the Data Fields"
        ) {
            //On Click method
        }
        Text(
            text = "FL DataTracker",
            modifier = Modifier.weight(10f),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h5
        )
        SimpleIconButton(
            modifier = Modifier
                .weight(1f),
            icon = Icons.Default.Search,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = "Search Icon to toggle Search Bar and search for Data"
        ) {
            viewModel.onEvent(HomeScreenEvent.ToggleSearchBarShow)
            Toast.makeText(context, "Showing toast....", Toast.LENGTH_SHORT).show()
        }
    }
}