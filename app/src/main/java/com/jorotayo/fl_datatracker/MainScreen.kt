package com.jorotayo.fl_datatracker

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.navigation.MainNavGraph
import com.jorotayo.fl_datatracker.navigation.MainScreens
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.zero

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@DefaultDualPreview
@Composable
fun BottomBarPreview() {
    FL_DatatrackerTheme {
        BottomBar(
            navController = rememberNavController()
        )
    }
}

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
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
//        Divider(
//            modifier = Modifier,
//            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.2f)
//        )
        BottomNavigation(
            backgroundColor = colors.background,
            elevation = zero
        ) {
            mainScreens.forEach { screen ->
                AnimatedBottomNavItem(
                    mainScreens = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.BottomNavItem(
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
                modifier = Modifier.padding(top = xxSmall),
                text = mainScreens.title,
                style = typography.body2
            )
        },
        icon = {
            Icon(
                imageVector = mainScreens.icon,
                contentDescription = mainScreens.pageDescription
            )
        },
        selected = itemSelected,
        unselectedContentColor = colors.secondary,
        selectedContentColor = colors.primary,
        onClick = {
            navController.navigate(mainScreens.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        modifier = Modifier.height(80.dp)
    )
}

@Composable
fun RowScope.AnimatedBottomNavItem(
    mainScreens: MainScreens,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val trimmedRoute = trimRoute(currentDestination?.route.toString())
    val itemSelected =
        currentDestination?.hierarchy?.any { trimmedRoute == mainScreens.route } == true

    val scale = if (itemSelected) 1.3f else 1f

    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        animationSpec = TweenSpec(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "Nav Bar Icon size animation"
    )
    val animatedVisibleColor by animateColorAsState(
        targetValue = colors.secondary,
        animationSpec = TweenSpec(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "Nav Bar visible color animation"
    )

    val animatedHiddenColor by animateColorAsState(
        targetValue = colors.primary,
        animationSpec = TweenSpec(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "Nav Bar color animation"
    )

    BottomNavigationItem(
        label = {
            Text(
                modifier = Modifier.padding(top = xSmall * scale),
                text = mainScreens.title,
                style = typography.body2,
                fontSize = typography.body2.fontSize * scale
            )
        },
        icon = {
            Icon(
                modifier = Modifier.scale(animatedScale),
                imageVector = mainScreens.icon,
                contentDescription = mainScreens.pageDescription
            )
        },
        selected = itemSelected,
        unselectedContentColor = animatedVisibleColor,
        selectedContentColor = animatedHiddenColor,
        onClick = {
            navController.navigate(mainScreens.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        modifier = Modifier.height(80.dp)
    )
}

@Composable
fun RowScope.ChipBottomNavItem(
    mainScreens: MainScreens,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val trimmedRoute = trimRoute(currentDestination?.route.toString())
    val itemSelected =
        currentDestination?.hierarchy?.any { trimmedRoute == mainScreens.route } == true

    val scale = if (itemSelected) 1.3f else 1f

    BottomNavigationItem(

        label = {
            Text(
                text = mainScreens.title,
                style = typography.body2,
                fontSize = typography.body2.fontSize * scale,
                color = colors.secondary
            )
        },
        icon = {
            Icon(
                imageVector = mainScreens.icon,
                contentDescription = mainScreens.pageDescription
            )
        },
        selected = itemSelected,
        unselectedContentColor = colors.background,
        selectedContentColor = colors.primary,
        onClick = {
            navController.navigate(mainScreens.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        modifier = Modifier.height(80.dp)
    )
}

/**
 * Trims and adjusts routes to show the correctly selected item
 */
fun trimRoute(route: String): String {
    return if (route.contains("?id=")) {
        route.substringBefore("?id=", route)
    } else if (route.contains("settings")) {
        "settings_screen"
    } else {
        route
    }
}
