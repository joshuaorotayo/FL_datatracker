package com.jorotayo.fl_datatracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEntryScreen
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldsScreen
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreen
import com.jorotayo.fl_datatracker.screens.welcomeScreen.WelcomeScreen
import com.jorotayo.fl_datatracker.screens.welcomeScreen.components.WelcomeScreenData
import com.jorotayo.fl_datatracker.viewModels.DataEntryScreenViewModel
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel
import com.jorotayo.fl_datatracker.viewModels.WelcomeScreenViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = MainScreens.Welcome.route) {
            val welcomeScreenViewModel = hiltViewModel<WelcomeScreenViewModel>()
            WelcomeScreen(
                navController = navController,
                onWelcomeEvent = welcomeScreenViewModel::onEvent,
                pages = getWelcomePages()
            )
        }
        composable(route = MainScreens.HomeMainScreens.route) {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            val dataEntryScreenViewModel = hiltViewModel<DataEntryScreenViewModel>()

            HomeScreen(
                state = homeScreenViewModel.uiState.value,
                navController = navController,
                onHomeEvent = homeScreenViewModel::onEvent,
                onDataEvent = dataEntryScreenViewModel::onDataEvent,
            )
        }
        settingsNavGraph(navController)
        /*composable(route = MainScreens.Settings.route) {
            SettingsScreen({
                navController.navigate(SettingsScreens.DisplaySettings.name) {
                    popUpTo(SettingsScreens.SettingsHome.name)
                    launchSingleTop = true
                }
            }, {
                navController.navigate(SettingsScreens.DataFieldsSettings.name) {
                    popUpTo(SettingsScreens.SettingsHome.name)
                    launchSingleTop = true
                }
            }) {
                navController.navigate(SettingsScreens.FAQsListSettings.name) {
                    popUpTo(SettingsScreens.SettingsHome.name)
                    launchSingleTop = true
                }
            }
        }*/
        composable(
            route = MainScreens.DataEntry.route + "?id={dataId}",
            arguments = listOf(
                navArgument(
                    name = "dataId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
            )
        ) {
            val dataEntryScreenViewModel = hiltViewModel<DataEntryScreenViewModel>()
            DataEntryScreen(
                navController = navController,
                uiState = dataEntryScreenViewModel.uiState.value,
                onUiEvent = dataEntryScreenViewModel.eventFlow,
                onDataEvent = dataEntryScreenViewModel::onDataEvent
            )

        }
        composable(
            route = MainScreens.DataFieldsMainScreens.route
        ) {
            val dataFieldsViewModel = hiltViewModel<DataFieldsViewModel>()

            DataFieldsScreen(
                state = dataFieldsViewModel.dataFieldScreenState.value,
                onUiEvent = dataFieldsViewModel.eventFlow,
                onDataFieldEvent = dataFieldsViewModel::onDataFieldEvent,
                onPresetEvent = dataFieldsViewModel::onPresetEvent,
                onRowEvent = dataFieldsViewModel::onRowEvent
            )
        }
    }
}

object Graph {
    const val MAIN = "main_graph"
    const val SETTINGS = "settings_graph"
}

private fun getWelcomePages(): List<WelcomeScreenData> {
    val pages = ArrayList<WelcomeScreenData>()
    pages.add(
        WelcomeScreenData(
            image = Icons.Default.SwapHoriz,
            title = "Swipe To Navigate",
            description = "Swipe on the images to traverse through the welcoming screen"
        )
    )

    pages.add(
        WelcomeScreenData(
            image = Icons.Default.Add,
            title = "Add Data",
            description = "Click the floating action button to add new Data"
        )
    )

    pages.add(
        WelcomeScreenData(
            image = Icons.Default.Checklist,
            title = "Enable/Disable Fields",
            description = "Configure the fields you wish to Enable and Disable in Settings"
        )
    )

    pages.add(
        WelcomeScreenData(
            image = Icons.Default.QueryStats,
            title = "View Data",
            description = "Have an overview of Data entered from the home-screen"
        )
    )

    pages.add(
        WelcomeScreenData(
            image = Icons.Default.AutoGraph,
            title = "Statistics",
            description = "View the breakdown of information in statistical form."
        )
    )

    return pages
}