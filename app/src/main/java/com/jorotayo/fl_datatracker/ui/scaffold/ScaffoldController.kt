package com.jorotayo.fl_datatracker.ui.scaffold

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ScaffoldController {

    var state by mutableStateOf(AppScaffoldState())
        private set

    fun update(newState: AppScaffoldState) {
        state = newState
    }

    fun reset() {
        state = AppScaffoldState()
    }
}

val LocalScaffoldController = compositionLocalOf<ScaffoldController> {
    error("No ScaffoldController provided")
}