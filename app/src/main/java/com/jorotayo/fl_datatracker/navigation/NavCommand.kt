package com.jorotayo.fl_datatracker.navigation

sealed class NavCommand {
    data class ToRoute(val route: String) : NavCommand()
    object Back : NavCommand()
}