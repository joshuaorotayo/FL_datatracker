package com.jorotayo.fl_datatracker.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
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
import com.jorotayo.fl_datatracker.viewModels.DataEntryScreenViewModel
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel
import com.jorotayo.fl_datatracker.viewModels.WelcomeViewModel

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
            WelcomeScreen(
                navController = navController,
                viewModel = WelcomeViewModel(),
                pages = pages
            )
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                viewModel = HomeScreenViewModel(),
                navController = navController
            )
        }
        composable(
            route = Screen.DataEntry.route + "?dataId={dataId}",
            arguments = listOf(
                navArgument(
                    name = "dataId"
                ) {
                    type = NavType.LongType
                    defaultValue = -1
                })
        ) {
            val dataId = it.arguments?.getLong("dataId", -1)
            DataEntryScreen(
                viewModel = DataEntryScreenViewModel(),
                navController = navController,
                dataId = dataId!!
            )
        }
        composable(route = Screen.DataFieldsScreen.route) {
            DataFieldsScreen(
                navController = navController,
                viewModel = DataFieldsViewModel()
            )
        }
    }

}