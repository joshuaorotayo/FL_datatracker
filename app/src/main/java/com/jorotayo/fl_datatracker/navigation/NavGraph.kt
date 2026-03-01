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
import com.jorotayo.fl_datatracker.navigation.Screen.DataEntry.editRoute
import com.jorotayo.fl_datatracker.navigation.Screen.DataEntry.newRoute
import com.jorotayo.fl_datatracker.ui.screens.dataEntry.DataEntryScreen
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
        title = "Data Forms"
    )

    object Home : Screen(
        route = "home",
        showBottomBar = true,
        icon = Icons.Default.Home,
        title = "Home"
    )

    /**
     * Two entry points share one destination:
     *
     *  • New record  → [newRoute]  — no args; DataEntryViewModel reads preset
     *                                from UserPreferenceStore automatically.
     *  • Edit record → [editRoute] — carries recordId; ViewModel loads the
     *                                record then looks up its preset.
     */
    object DataEntry : Screen(
        route = "dataEntry?recordId={recordId}",
        showBottomBar = true,
        icon = Icons.Default.EditNote,
        title = "Data Entry"
    ) {
        /** Navigate here to create a new record (preset comes from prefs). */
        fun newRoute(): String = "dataEntry"

        /** Navigate here to view/edit an existing record. */
        fun editRoute(recordId: Long): String = "dataEntry?recordId=$recordId"
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
    startDestination: String,
    navController: NavHostController
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
            HomeScreen(navController)
        }

        composable(route = Screen.DataForm.route) {
            DataFormScreen()
        }

        composable(
            route = Screen.DataEntry.route,
            arguments = listOf(
                navArgument("recordId") {
                    type = NavType.LongType
                    defaultValue = -1L   // -1 = new record mode
                }
            )
        ) { backStackEntry ->
            val recordId = backStackEntry.arguments?.getLong("recordId")
                ?.takeIf { it != -1L }  // convert sentinel back to null

            DataEntryScreen(
                recordId = recordId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Settings.route) {
            // SettingsScreen()
        }
    }
}