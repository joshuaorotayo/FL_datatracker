package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.ui.focus.FocusState

sealed class HomeScreenEvent {
    object ResetSearchBar : HomeScreenEvent()
    object ToggleSearchBarShow : HomeScreenEvent()
    object ToggleSearchBarHide : HomeScreenEvent()
    object EditDataItem : HomeScreenEvent()
    data class SearchFocusChanged(val focusState: FocusState) : HomeScreenEvent()
    data class SearchItemEntered(val searchItem: String) : HomeScreenEvent()
    data class ToggleSearchBar(val value: Boolean) : HomeScreenEvent()
}
