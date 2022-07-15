package com.jorotayo.fl_datatracker.screens.onBoardingScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class OnBoardingPage(
    val image: ImageVector,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = Icons.Default.DataExploration,
        title = "Add Data",
        description = "Click the floating action button to add new Data"
    )

    object Second : OnBoardingPage(
        image = Icons.Default.ToggleOn,
        title = "Enable/Disable Fields",
        description = "Configure the fields you wish to Enable and Disable in Settings"
    )

    object Third : OnBoardingPage(
        image = Icons.Default.QueryStats,
        title = "View Data",
        description = "Have an overview of Data entered from the homescreen"
    )

    object Fourth : OnBoardingPage(
        image = Icons.Default.AutoGraph,
        title = "Statistics",
        description = "View the breakdown of information in statistical form."
    )
}
