package com.jorotayo.fl_datatracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.jorotayo.fl_datatracker.screens.settings.DataFieldSettings
import com.jorotayo.fl_datatracker.screens.settings.DisplaySettings
import com.jorotayo.fl_datatracker.screens.settings.FAQsList
import com.jorotayo.fl_datatracker.screens.settings.SettingsScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController
) {
    navigation(
        route = "settings_nav",
        startDestination = "settings_screen"
    ) {
        composable(route = MainScreens.Settings.route) {
            SettingsScreen(
                onDataFieldSettingsClick = {
                    navController.navigate(SettingScreens.DataFieldSettings.route) {
                        popUpTo("settings_screen")
                        launchSingleTop = true
                    }
                },
                onDisplaySettingsClick = {
                    navController.navigate(SettingScreens.DisplaySettings.route) {
                        popUpTo("settings_screen")
                        launchSingleTop = true
                    }
                },
                onFAQSListClick = {
                    navController.navigate(SettingScreens.FAQsList.route) {
                        popUpTo("settings_screen")
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = SettingScreens.DataFieldSettings.route) {
            DataFieldSettings()
        }
        composable(route = SettingScreens.DisplaySettings.route) {
            DisplaySettings()
        }
        composable(route = SettingScreens.FAQsList.route) {
            FAQsList()
        }
    }
}