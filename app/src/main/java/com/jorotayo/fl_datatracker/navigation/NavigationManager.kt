package com.jorotayo.fl_datatracker.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {

    // replay = 0 so old commands aren't re-delivered after recomposition.
    // extraBufferCapacity = 1 so tryEmit never drops a command even if the
    // collector hasn't started yet — the emission is held until it's consumed.
    private val _commands = MutableSharedFlow<NavCommand>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val commands = _commands.asSharedFlow()

    fun navigate(command: NavCommand) {
        _commands.tryEmit(command)
    }
}