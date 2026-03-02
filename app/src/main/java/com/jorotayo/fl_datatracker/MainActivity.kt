package com.jorotayo.fl_datatracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jorotayo.fl_datatracker.navigation.MainNavGraph
import com.jorotayo.fl_datatracker.navigation.NavCommand
import com.jorotayo.fl_datatracker.navigation.NavigationManager
import com.jorotayo.fl_datatracker.ui.components.FloatingBottomBar
import com.jorotayo.fl_datatracker.ui.scaffold.LocalScaffoldController
import com.jorotayo.fl_datatracker.ui.scaffold.ScaffoldController
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import com.jorotayo.fl_datatracker.ui.theme.ThemeViewModel
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMedium
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val startDestination by mainViewModel.startDestination.collectAsState()

            val themeViewModel = hiltViewModel<ThemeViewModel>()
            val useDeviceDarkMode by themeViewModel.useDeviceDarkMode.collectAsState()
            val darkTheme = if (useDeviceDarkMode) isSystemInDarkTheme() else false

            // ✅ Create controller once at root
            val scaffoldController = remember { ScaffoldController() }
            val scaffoldState = scaffoldController.state

            FL_DatatrackerThemeNew(darkTheme = darkTheme) {

                CompositionLocalProvider(
                    LocalScaffoldController provides scaffoldController
                ) {

                    Box(modifier = Modifier.fillMaxSize()) {

                        if (startDestination is AppStartDestination.Ready) {

                            val destination = startDestination as AppStartDestination.Ready
                            val navController = rememberNavController()

                            LaunchedEffect(navController) {
                                navigationManager.commands.collect { command ->
                                    when (command) {
                                        is NavCommand.ToRoute -> navController.navigate(command.route) {
                                            command.popUpTo?.let { popUpToRoute ->
                                                popUpTo(popUpToRoute) {
                                                    inclusive = command.inclusive
                                                }
                                            }
                                            launchSingleTop = true
                                        }

                                        NavCommand.Back -> {
                                            if (!navController.popBackStack()) finish()
                                        }
                                    }
                                }
                            }

                            Scaffold(
                                topBar = {
                                    TopAppBar(
                                        modifier = Modifier.padding(top = spacingMedium),
                                        title = { scaffoldState.title() },
                                        navigationIcon = {
                                            scaffoldState.navigationIcon?.invoke()
                                        },
                                        actions = {
                                            scaffoldState.actions?.invoke()
                                        },
                                        colors = TopAppBarDefaults.topAppBarColors(
                                            containerColor = MaterialTheme.colorScheme.background,
                                            titleContentColor = MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                },
                                bottomBar = {
                                    if (scaffoldState.showBottomBar) {
                                        FloatingBottomBar(navController = navController)
                                    }
                                },
                                floatingActionButton = {
                                    scaffoldState.fab?.invoke()
                                }
                            ) { paddingValues ->

                                MainNavGraph(
                                    navController = navController,
                                    startDestination = destination.route,
                                    modifier = Modifier.padding(paddingValues)
                                )
                            }
                        }

                        AnimatedVisibility(
                            visible = startDestination is AppStartDestination.Loading,
                            enter = fadeIn(),
                            exit = fadeOut(animationSpec = tween(300)),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.EditNote,
                                    contentDescription = null,
                                    modifier = Modifier.size(72.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}