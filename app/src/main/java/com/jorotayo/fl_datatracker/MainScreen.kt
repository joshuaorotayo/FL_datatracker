package com.jorotayo.fl_datatracker

import android.annotation.SuppressLint
import android.content.res.Configuration
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.navigation.MainNavGraph
import com.jorotayo.fl_datatracker.navigation.MainScreens
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.zero

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun MainScreensPreview() {
    FL_DatatrackerTheme {
        MainScreen()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
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
        Divider(
            modifier = Modifier,
            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.2f)
        )
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface,
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

/*

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
                style = MaterialTheme.typography.body2
            )
        },
        icon = {
            Icon(
                imageVector = mainScreens.icon,
                contentDescription = mainScreens.pageDescription
            )
        },
        selected = itemSelected,
        unselectedContentColor = MaterialTheme.colors.secondary,
        selectedContentColor = MaterialTheme.colors.primary,
        onClick = {
            navController.navigate(mainScreens.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        modifier = Modifier.height(80.dp)
    )
}
*/
@Composable
fun RowScope.AnimatedBottomNavItem(
    mainScreens: MainScreens,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val trimmedRoute = trimRoute(currentDestination?.route.toString())
    val itemSelected =
        currentDestination?.hierarchy?.any { trimmedRoute == mainScreens.route } == true

    val scale = if (itemSelected) 1.5f else 1f

    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        animationSpec = TweenSpec(
            durationMillis = 2000,
            easing = FastOutSlowInEasing
        ), label = "Nav Bar Icon size animation"
    )
    val animatedVisibleColor by animateColorAsState(
        targetValue = MaterialTheme.colors.secondary,
        animationSpec = TweenSpec(
            durationMillis = 2000,
            easing = FastOutSlowInEasing
        ), label = "Nav Bar visible color animation"
    )

    val animatedHiddenColor by animateColorAsState(
        targetValue = MaterialTheme.colors.primary,
        animationSpec = TweenSpec(
            durationMillis = 2000,
            easing = FastOutSlowInEasing
        ), label = "Nav Bar color animation"
    )

    BottomNavigationItem(
        label = {
            Text(
                modifier = Modifier.padding(top = xxSmall),
                text = mainScreens.title,
                style = MaterialTheme.typography.body2
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
