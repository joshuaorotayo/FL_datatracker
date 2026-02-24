package com.jorotayo.fl_datatracker

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import com.jorotayo.fl_datatracker.navigation.MainNavGraph
import com.jorotayo.fl_datatracker.navigation.NavCommand
import com.jorotayo.fl_datatracker.navigation.NavigationManager
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import com.jorotayo.fl_datatracker.ui.theme.ThemeViewModel
import com.jorotayo.fl_datatracker.ui.util.SharedSettingService
import com.jorotayo.fl_datatracker.ui.util.components.FloatingBottomBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedSettingService: SharedSettingService

    @Inject
    lateinit var userPreferenceStore: UserPreferenceStore

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        var keepSplashOnScreen = true
        val delay = 1000L

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)


        super.onCreate(savedInstanceState)

        var startDestination = Screen.Home.route  // default

        lifecycleScope.launch {
            sharedSettingService.initialiseValues()
            val onboardingComplete = sharedSettingService.isOnboardingComplete()
            startDestination = if (onboardingComplete) Screen.Home.route
            else Screen.Onboarding.route

            setContent {

                val themeViewModel: ThemeViewModel = hiltViewModel()
                val useDeviceDarkMode by themeViewModel.useDeviceDarkMode.collectAsState()
                val systemInDarkTheme = isSystemInDarkTheme()

                val darkTheme = if (useDeviceDarkMode) systemInDarkTheme else false

                FL_DatatrackerThemeNew(darkTheme = darkTheme) {

                    val navController = rememberNavController()
                    val context = LocalContext.current

                    LaunchedEffect(navController) {
                        navigationManager.commands.collect { command ->
                            when (command) {
                                is NavCommand.ToRoute -> navController.navigate(command.route) {
                                    command.popUpTo?.let { popUpToRoute ->
                                        popUpTo(popUpToRoute) { inclusive = command.inclusive }
                                    }
                                    launchSingleTop = true
                                }

                                NavCommand.Back -> {
                                    if (!navController.popBackStack()) {
                                        (context as? Activity)?.finish()
                                    } else {
                                        navController.popBackStack()
                                    }
                                }

                            }
                        }
                    }

                    val currentBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = currentBackStackEntry?.destination?.route

                    val bottomBarMap = listOf(Screen.Home, Screen.DataForm, Screen.Settings)
                        .associateBy { it.route }

                    fun String?.shouldShowBottomBar(): Boolean {
                        return bottomBarMap[this]?.showBottomBar == true
                    }

                    Scaffold(
                        contentWindowInsets = WindowInsets(0),
                        bottomBar = {
                            if (currentRoute.shouldShowBottomBar()) {
                                FloatingBottomBar(navController = navController)
                            }
                        }
                    ) { innerPadding ->
                        MainNavGraph(
                            navController = navController,
                            startDestination = startDestination,
                            modifier = Modifier.padding(
                                bottom = innerPadding.calculateBottomPadding()
                            )
                        )
                    }
                }
            }
        }
    }
}
