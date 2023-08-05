package com.jorotayo.fl_datatracker

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.jorotayo.fl_datatracker.navigation.MainNavGraph
import com.jorotayo.fl_datatracker.navigation.MainScreens

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        MainNavGraph(navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val mainScreens = listOf(
        MainScreens.DataFieldsMainScreens,
        MainScreens.HomeMainScreens,
        MainScreens.DataEntry,
        MainScreens.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        mainScreens.forEach { screen ->
            AddItem(
                mainScreens = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    mainScreens: MainScreens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val trimmedRoute = trimRoute(currentDestination?.route.toString())
    val itemSelected =
        currentDestination?.hierarchy?.any { trimmedRoute == mainScreens.route } == true
    BottomNavigationItem(
        label = {
            Text(
                text = mainScreens.title,
                style = MaterialTheme.typography.body1
            )
        },
        icon = {
            Icon(
                imageVector = mainScreens.icon,
                contentDescription = mainScreens.pageDescription
            )
        },
        selected = itemSelected,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        selectedContentColor = MaterialTheme.colors.background,
        onClick = {
            navController.navigate(mainScreens.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

/**
 *
 */
fun trimRoute(route: String): String {
    Log.i(TAG, "trimRoute: " + route.substringBefore("?id=", route))
    return if (route.contains("?id=")) {
        route.substringBefore("?id=", route)
    } else if (route.contains("settings")) {
        "settings_screen"
    } else {
        route
    }
}
