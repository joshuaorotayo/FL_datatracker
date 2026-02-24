package com.jorotayo.fl_datatracker.navigation

sealed class NavCommand {
    data class ToRoute(
        val route: String,
        val popUpTo: String? = null,
        val inclusive: Boolean = false
    ) : NavCommand()
    object Back : NavCommand()
}
