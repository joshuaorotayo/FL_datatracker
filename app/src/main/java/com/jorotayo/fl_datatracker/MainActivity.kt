package com.jorotayo.fl_datatracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jorotayo.fl_datatracker.navigation.MainNavGraph
import com.jorotayo.fl_datatracker.navigation.NavigationManager
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.ui.components.FloatingBottomBar
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import com.jorotayo.fl_datatracker.ui.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Force lazy delegate to initialize
        mainViewModel.let { }

        setContent {
            val navController = rememberNavController()
            val startDestination by mainViewModel.startDestination.collectAsState()

            val themeViewModel = hiltViewModel<ThemeViewModel>()
            val useDeviceDarkMode by themeViewModel.useDeviceDarkMode.collectAsState()
            val darkTheme = if (useDeviceDarkMode) isSystemInDarkTheme() else false

            FL_DatatrackerThemeNew(darkTheme = darkTheme) {

                Scaffold(
                    bottomBar = {
                        val currentBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = currentBackStackEntry?.destination?.route
                        val bottomBarRoutes = setOf(
                            Screen.Home.route,
                            Screen.DataForm.route,
                            Screen.Settings.route
                        )

                        if (currentRoute in bottomBarRoutes) {
                            FloatingBottomBar(navController)
                        }
                    }
                ) { paddingValues ->

                    // ALWAYS mount the NavHost inside Scaffold content
                    MainNavGraph(
                        navController = navController,
                        startDestination = (startDestination as? AppStartDestination.Ready)?.route
                            ?: Screen.Home.route,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}