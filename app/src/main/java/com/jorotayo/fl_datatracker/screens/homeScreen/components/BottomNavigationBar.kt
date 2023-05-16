package com.jorotayo.fl_datatracker.screens.homeScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.xSmall

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light Mode")
@Composable
fun PreviewBottomNavigationBar() {
    FL_DatatrackerTheme {
        BottomNavigationBar(
            navController = rememberNavController(),
            items = listOf(
                Screen.DataFieldsScreen,
                Screen.HomeScreen,
                Screen.DataEntry
            )
        )
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<Screen>
) {

    val highlightColour =
        if (isSystemInDarkTheme()) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary
    val unselectedColour = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = xSmall)
    ) {
        val currentRoute = sanitiseRoute(navController)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        (if (screen.route.contains(currentRoute)) screen.selectedIcon else screen.unselectedIcon)?.let {
                            Column(
                                modifier = Modifier,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    modifier = Modifier,
                                    imageVector = it,
                                    contentDescription = screen.pageDescription,
                                    tint = if (screen.route.contains(currentRoute)) highlightColour else unselectedColour,
                                )
                                Text(
                                    modifier = Modifier,
                                    text = screen.pageName,
                                    color = if (currentRoute == screen.route) highlightColour else unselectedColour
                                )
                            }
                        }
                    },
                    selected = currentRoute == screen.route,
                    alwaysShowLabel = false, // This hides the title for the unselected items
                    onClick = {
                        // This if check gives us a "singleTop" behavior where we do not create a
                        // second instance of the composable if we are already on that destination
                        if (currentRoute != screen.route) {
                            navController.navigate(screen.route)
                        }
                    }
                )
            }
        }
    }
}

private fun sanitiseRoute(navController: NavController): String {
    var currentDestination: String =
        if (navController.currentBackStackEntry?.destination?.route?.contains("?") == true) {
            val splitString = navController.currentBackStackEntry?.destination?.route?.split("?")
            splitString!![0]
        } else {
            navController.currentBackStackEntry?.destination?.route.toString()
        }
    return currentDestination
}