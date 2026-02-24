package com.jorotayo.fl_datatracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jorotayo.fl_datatracker.ui.screens.dataForm.DataFormScreen
import com.jorotayo.fl_datatracker.ui.screens.home.HomeScreen
import com.jorotayo.fl_datatracker.ui.screens.onboarding.OnboardingScreen

// =============================================================================
// ROUTES
// =============================================================================
sealed class Screen(
    val route: String,
    val showBottomBar: Boolean = false,
    val icon: ImageVector? = null,
    val title: String? = null
) {
    object Onboarding : Screen(
        route = "onboarding",
        showBottomBar = false,
        icon = Icons.Default.List,
        title = "Onboarding"
    )

    object DataForm : Screen(
        route = "dataForm",
        showBottomBar = true,
        icon = Icons.Default.List,
        title = "Data Form"
    )

    object Home : Screen(
        route = "home",
        showBottomBar = true,
        icon = Icons.Default.Home,
        title = "Home"
    )

    object DataEntry : Screen(
        "dataEntry?recordId={recordId}",
        showBottomBar = false,
        icon = Icons.Default.EditNote,
        title = "Data Entry"
    ) {
        fun route(recordId: Long? = null): String {
            return if (recordId != null) {
                "dataEntry?recordId=$recordId"
            } else {
                "dataEntry"
            }
        }
    }

    object Settings : Screen(
        route = "settings",
        showBottomBar = true,
        icon = Icons.Default.Settings,
        title = "Settings"
    )
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
            OnboardingScreen()
        }

        composable(route = Screen.Home.route) {
            HomeScreen()
        }

        composable(route = Screen.DataForm.route) {
            DataFormScreen()
        }

        composable(
            route = Screen.DataEntry.route,
            arguments = listOf(
                navArgument("recordId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->

            backStackEntry.arguments?.getLong("recordId")

//            DataEntryScreen(
//                recordId = if (recordId == -1L) null else recordId
//            )
        }

        composable(route = Screen.Settings.route) {
            // SettingsScreen()
        }
    }
}
