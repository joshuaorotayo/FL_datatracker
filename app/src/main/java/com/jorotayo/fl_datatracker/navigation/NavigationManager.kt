package com.jorotayo.fl_datatracker.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class NavigationManager @Inject constructor() {
    private val _commands = MutableSharedFlow<NavCommand>(replay = 0)
    val commands = _commands.asSharedFlow()

    fun navigate(command: NavCommand) {
        _commands.tryEmit(command)
    }
}
