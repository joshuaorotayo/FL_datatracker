package com.jorotayo.fl_datatracker

sealed interface AppState {

    data object Loading : AppState

    data object Ready : AppState
}