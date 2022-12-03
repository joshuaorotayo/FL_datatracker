package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.navigation.Screen

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    BottomNavigationBar(navController = rememberNavController(), items = hiltViewModel())
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<Screen>
) {
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(16.dp)),
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
                                    tint = if (screen.route.contains(currentRoute)) MaterialTheme.colors.primary else Color.LightGray,
                                )
                                Text(
                                    text = screen.pageName,
                                    color = if (currentRoute == screen.route) MaterialTheme.colors.primary else Color.LightGray
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