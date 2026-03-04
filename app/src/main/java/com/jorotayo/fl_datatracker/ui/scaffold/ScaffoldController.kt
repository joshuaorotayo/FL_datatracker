package com.jorotayo.fl_datatracker.ui.scaffold

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData

@Stable
class ScaffoldController {

    private val _state = mutableStateOf(AppScaffoldState())
    private var toastCounter = 0L

    var state: AppScaffoldState
        get() = _state.value
        set(value) {
            _state.value = value
        }

    fun update(newState: AppScaffoldState) {
        _state.value = newState
    }

    fun showToast(toast: AppToastData) {
        toastCounter++
        _state.value = _state.value.copy(toast = toast, toastId = toastCounter)
    }

    fun clearToast() {
        _state.value = _state.value.copy(toast = null)
    }

    fun reset() {
        _state.value = AppScaffoldState()
    }
}

val LocalScaffoldController = compositionLocalOf<ScaffoldController> {
    error("No ScaffoldController provided")
}