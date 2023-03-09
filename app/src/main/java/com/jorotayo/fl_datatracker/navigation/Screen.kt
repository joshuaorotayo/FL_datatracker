package com.jorotayo.fl_datatracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TableRows
import androidx.compose.material.icons.outlined.Close
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val pageName: String,
    val pageDescription: String? = "",
    val selectedIcon: ImageVector? = Icons.Filled.Close,
    val unselectedIcon: ImageVector? = Icons.Outlined.Close,
) {
    object Welcome : Screen(
        route = "onboarding_screen",
        pageName = "Welcome"
    )

    object Settings : Screen(
        route = "settings_screen",
        pageName = "Settings"
    )

    object DataEntry : Screen(
        route = "data_entry_screen",
        pageName = "Data Entry",
        pageDescription = "Edit forms based on the Data Entry Screen",
        selectedIcon = Icons.Default.Edit,
        unselectedIcon = Icons.Filled.Edit
    )

    object HomeScreen : Screen(
        route = "home_screen",
        pageName = "Home",
        pageDescription = "Home Screen with page of data results",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Filled.Home
    )

    object DataFieldsScreen : Screen(
        route = "data_fields_screen",
        pageName = "Data Fields",
        pageDescription = "Add/Edit or remove Data Fields Screen",
        selectedIcon = Icons.Filled.TableRows,
        unselectedIcon = Icons.Filled.TableRows
    )
}