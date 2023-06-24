package com.jorotayo.fl_datatracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEntryScreen
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldsScreen
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreen
import com.jorotayo.fl_datatracker.screens.welcomeScreen.WelcomeScreen
import com.jorotayo.fl_datatracker.screens.welcomeScreen.components.WelcomeScreenData
import com.jorotayo.fl_datatracker.ui.PageTemplate
import com.jorotayo.fl_datatracker.viewModels.DataEntryScreenViewModel
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel
import com.jorotayo.fl_datatracker.viewModels.WelcomeScreenViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun SetupNavGraph(
    startDestination: String
) {
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

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Welcome.route) {
            val welcomeScreenViewModel = hiltViewModel<WelcomeScreenViewModel>()
            WelcomeScreen(
                navController = navController,
                onWelcomeEvent = welcomeScreenViewModel::onEvent,
                welcomeState = welcomeScreenViewModel.uiState.value,
                pages = pages
            )
        }
        composable(route = Screen.Settings.route) {
            PageTemplate(navController = navController)
        }
        composable(route = Screen.HomeScreen.route) {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            val dataEntryScreenViewModel = hiltViewModel<DataEntryScreenViewModel>()

            HomeScreen(
                homeState = homeScreenViewModel.uiState.value,
                navController = navController,
                onHomeEvent = homeScreenViewModel::onEvent,
                onDataEvent = dataEntryScreenViewModel::onDataEvent
            )
        }
        composable(
            route = Screen.DataEntry.route + "?id={dataId}",
            arguments = listOf(
                navArgument(
                    name = "dataId"
                ) {
                    type = NavType.LongType
                    defaultValue = -1
                },
            )
        ) { navBackStackEntry ->
            val dataId = navBackStackEntry.arguments?.getLong("dataId")

            val dataEntryScreenViewModel = hiltViewModel<DataEntryScreenViewModel>()
            if (dataId != null) {
                DataEntryScreen(
                    navController = navController,
                    uiState = dataEntryScreenViewModel.uiState.value,
                    onUiEvent = dataEntryScreenViewModel.eventFlow,
                    onDataEvent = dataEntryScreenViewModel::onDataEvent
                )
            }
        }
        composable(
            route = Screen.DataFieldsScreen.route
        ) {
            val dataFieldsViewModel = hiltViewModel<DataFieldsViewModel>()

            DataFieldsScreen(
                navController = navController,
                state = dataFieldsViewModel.dataFieldScreenState.value,
                onUiEvent = dataFieldsViewModel.eventFlow,
                onDataFieldEvent = dataFieldsViewModel::onDataFieldEvent,
                onPresetEvent = dataFieldsViewModel::onPresetEvent,
                onRowEvent = dataFieldsViewModel::onRowEvent,
            )
        }
    }
}