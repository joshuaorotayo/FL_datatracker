package com.jorotayo.fl_datatracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TableRows
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val pageDescription: String? = "",
    val icon: ImageVector = Icons.Filled.Close,
) {
    object Welcome : Screen(
        route = "onboarding_screen",
        title = "Welcome",
        icon = Icons.Default.WavingHand
    )

    object Settings : Screen(
        route = "settings_screen",
        title = "Settings",
        icon = Icons.Default.Settings
    )

    object DataEntry : Screen(
        route = "data_entry",
        title = "Data Entry",
        pageDescription = "Edit forms based on the Data Entry Screen",
        icon = Icons.Default.Edit
    )

    object HomeScreen : Screen(
        route = "home_screen",
        title = "Home",
        pageDescription = "Home Screen with page of data results",
        icon = Icons.Filled.Home
    )

    object DataFieldsScreen : Screen(
        route = "data_fields_screen",
        title = "Data Fields",
        pageDescription = "Add/Edit or remove Data Fields Screen",
        icon = Icons.Filled.TableRows
    )
}