package com.jorotayo.fl_datatracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppUiViewModel @Inject constructor() : ViewModel() {

    private val _appState = MutableStateFlow<AppState>(AppState.Loading)
    val appState: StateFlow<AppState> = _appState

    fun showGlobalLoading() {
        _appState.value = AppState.Loading
    }

    fun showApp() {
        _appState.value = AppState.Ready
    }

    // Screen-level control (default = true)
    private val _screenAllowsBottomBar = MutableStateFlow(true)

    // Scroll-level control (default = true)
    private val _scrollAllowsBottomBar = MutableStateFlow(true)

    // Public combined state
    val bottomBarVisible: StateFlow<Boolean> =
        combine(
            _screenAllowsBottomBar,
            _scrollAllowsBottomBar
        ) { screen, scroll ->
            screen && scroll
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            true
        )

    fun setScreenAllowsBottomBar(allowed: Boolean) {
        _screenAllowsBottomBar.value = allowed
    }

    fun setScrollAllowsBottomBar(allowed: Boolean) {
        _scrollAllowsBottomBar.value = allowed
    }
}