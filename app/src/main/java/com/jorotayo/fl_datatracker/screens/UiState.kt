package com.jorotayo.fl_datatracker.screens

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Empty : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    object Error : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
}