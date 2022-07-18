package com.jorotayo.fl_datatracker.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreen
import com.jorotayo.fl_datatracker.screens.onBoardingScreen.OnBoardingScreen
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.OnBoarding.route) {
            OnBoardingScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                viewModel = HomeScreenViewModel(),
                navController = navController
            )
        }
    }
}