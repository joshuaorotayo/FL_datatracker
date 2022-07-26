package com.jorotayo.fl_datatracker.screens.homeScreen.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            .fillMaxWidth(),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        Text(
            text = "FL DataTracker",
            modifier = Modifier
                .weight(10f)
                .padding(start = 20.dp),
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h5
        )
        SimpleIconButton(
            modifier = Modifier
                .size(36.dp)
                .weight(1f),
            icon = Icons.Default.Search,
            tint = MaterialTheme.colors.primary,
            contentDescription = "Search Icon to toggle Search Bar and search for Data"
        ) {
            viewModel.onEvent(HomeScreenEvent.ToggleSearchBarShow)
            Toast.makeText(context, "Showing toast....", Toast.LENGTH_SHORT).show()
        }
    }
}