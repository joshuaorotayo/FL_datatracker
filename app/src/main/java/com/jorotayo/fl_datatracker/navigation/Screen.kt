package com.jorotayo.fl_datatracker.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen(route = "onboarding_screen")
    object Home : Screen(route = "home_screen")
    object DataEntry : Screen(route = "dataEntry_screen")
}