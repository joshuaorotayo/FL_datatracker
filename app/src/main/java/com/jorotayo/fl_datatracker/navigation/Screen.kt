package com.jorotayo.fl_datatracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TableRows
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.TableRows
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val pageDescription: String? = "",
    val selectedIcon: ImageVector? = Icons.Filled.Close,
    val unselectedIcon: ImageVector? = Icons.Outlined.Close
) {
    object Welcome : Screen(route = "onboarding_screen")
    object DataEntry : Screen(route = "dataEntry_screen")

    object HomeScreen : Screen(
        route = "home_screen",
        pageDescription = "Home Screen with page of data results",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Filled.Home
    )

    object DataFieldsScreen : Screen(
        "data_fields_screen",
        "Add/Edit or remove Data Fields Screen",
        selectedIcon = Icons.Filled.TableRows,
        unselectedIcon = Icons.Outlined.TableRows
    )
}