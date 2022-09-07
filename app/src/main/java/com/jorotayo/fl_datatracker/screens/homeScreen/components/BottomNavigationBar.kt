package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        (if (currentRoute!!.contains(screen.route)) screen.selectedIcon else screen.unselectedIcon)?.let {
                            Column(
                                modifier = Modifier,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    modifier = Modifier,
                                    imageVector = it,
                                    contentDescription = screen.pageDescription,
                                    tint = if (currentRoute == screen.route) MaterialTheme.colors.primary else Color.Gray,
                                )
                                Text(
                                    text = screen.pageName,
                                    color = if (currentRoute == screen.route) MaterialTheme.colors.primary else Color.Gray
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