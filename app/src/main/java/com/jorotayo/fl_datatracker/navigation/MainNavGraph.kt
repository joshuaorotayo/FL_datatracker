package com.jorotayo.fl_datatracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEntryScreen
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEntryScreenViewModel
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldsScreen
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldsViewModel
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreen
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreenViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = "main_nav",
        startDestination = "home_screen"
    ) {
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
                uiState = dataFieldsViewModel.uiState.value,
                onUiEvent = dataFieldsViewModel.eventFlow,
                onDataFieldEvent = dataFieldsViewModel::onDataFieldEvent,
                onPresetEvent = dataFieldsViewModel::onPresetEvent,
                onRowEvent = dataFieldsViewModel::onRowEvent
            )
        }
    }
}
