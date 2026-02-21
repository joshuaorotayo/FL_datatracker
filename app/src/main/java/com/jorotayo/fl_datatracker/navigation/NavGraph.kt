package com.jorotayo.fl_datatracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.AllFieldsPreviewScreen
import com.jorotayo.fl_datatracker.ui.screens.home.HomeScreen
import com.jorotayo.fl_datatracker.ui.screens.onboarding.OnboardingEvent
import com.jorotayo.fl_datatracker.ui.screens.onboarding.OnboardingScreen
import com.jorotayo.fl_datatracker.ui.screens.onboarding.OnboardingScreenState

// =============================================================================
// ROUTES
// =============================================================================

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DataForm : Screen("dataForm")
    object DataEntry : Screen("dataEntry/{presetId}") {
        fun route(presetId: Long) = "dataEntry/$presetId"
    }

    object Settings : Screen("settings")
    object Onboarding : Screen("onboarding")
}

// =============================================================================
// NAV GRAPH
// =============================================================================

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                state = OnboardingScreenState(),
                onEvent = { event ->
                    when (event) {
                        is OnboardingEvent.SaveOnBoarding -> {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Onboarding.route) { inclusive = true }
                            }
                        }
                    }
                }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen()
        }

        composable(route = Screen.DataForm.route) {
            // ImprovedDataFieldsScreen(
            //     state = ...,
            //     onEvent = { event ->
            //         when (event) {
            //             is DataFieldsEvent.AddField -> { ... }
            //             else -> { ... }
            //         }
            //     }
            // )
        }

        composable(
            route = Screen.DataEntry.route,
            arguments = listOf(
                navArgument("presetId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong("presetId") ?: 0L
            AllFieldsPreviewScreen(
//                presetId = presetId,
                onNavigateBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Settings.route) {
            // SettingsScreen()
        }
    }
}