package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        modifier = Modifier,
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 22.dp
    ) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route

        Row(
            modifier = Modifier.fillMaxWidth(0.7f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        (if (currentRoute == screen.route) screen.selectedIcon else screen.unselectedIcon)?.let {
                            Icon(
                                modifier = Modifier,
                                //                                imageVector = it,
                                imageVector = it,
                                contentDescription = screen.pageDescription,
                                tint = MaterialTheme.colors.background,
                            )
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