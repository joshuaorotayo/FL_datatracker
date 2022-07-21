package com.jorotayo.fl_datatracker.screens.mainScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TableRows
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationScreens(
    val route: String,
    val pageDescription: String,
    val icon: ImageVector
) {
    object HomeScreen : BottomNavigationScreens(
        "home_screen",
        "Home Screen with page of data results",
        Icons.Default.Home
    )

    object DataFieldsScreen : BottomNavigationScreens(
        "data_fields_screen",
        "Add/Edit or remove Data Fields Screen",
        Icons.Default.TableRows
    )
}
