package com.jorotayo.fl_datatracker.screens.mainScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {

}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.HomeScreen,
        BottomNavigationScreens.DataFieldsScreen,
    )
}