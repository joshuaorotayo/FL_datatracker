package com.jorotayo.fl_datatracker.ui.scaffold

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

@Stable
class ScaffoldController {

    private val _state = mutableStateOf(AppScaffoldState())

    var state: AppScaffoldState
        get() = _state.value
        set(value) {
            _state.value = value
        }

    fun update(newState: AppScaffoldState) {
        _state.value = newState
    }

    fun reset() {
        _state.value = AppScaffoldState()
    }
}

val LocalScaffoldController = compositionLocalOf<ScaffoldController> {
    error("No ScaffoldController provided")
}