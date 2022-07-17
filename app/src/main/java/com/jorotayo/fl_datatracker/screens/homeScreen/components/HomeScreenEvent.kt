package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.ui.focus.FocusState

sealed class HomeScreenEvent {
    object IncrementCount : HomeScreenEvent()
    object DecrementCount : HomeScreenEvent()
    object ResetSearchBar : HomeScreenEvent()
    data class SearchFocusChanged(val focusState: FocusState) : HomeScreenEvent()
    data class SearchItemEntered(val searchItem: String) : HomeScreenEvent()
    data class ToggleSearchBar(val value: Boolean) : HomeScreenEvent()
}
