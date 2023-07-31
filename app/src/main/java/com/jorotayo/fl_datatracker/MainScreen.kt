package com.jorotayo.fl_datatracker

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jorotayo.fl_datatracker.navigation.NavGraph
import com.jorotayo.fl_datatracker.navigation.Screen

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(startDestination: String) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        NavGraph(
            navController = navController,
            startDestination = startDestination
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screen.DataFieldsScreen,
        Screen.HomeScreen,
        Screen.DataEntry,
        Screen.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val trimmedRoute = trimRoute(currentDestination?.route.toString())
    val itemSelected = currentDestination?.hierarchy?.any { trimmedRoute == screen.route } == true
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = screen.pageDescription
            )
        },
        selected = itemSelected,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        selectedContentColor = MaterialTheme.colors.primary,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

fun trimRoute(route: String): String {
    Log.i(TAG, "trimRoute: " + route.substringBefore("?id=", route))
    return route.substringBefore("?id=", route)
}
