package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.ui.focus.FocusState

sealed class HomeScreenEvent {
    object ResetSearchBar : HomeScreenEvent()
    object ToggleSearchBar : HomeScreenEvent()
    object ShowSettingsView : HomeScreenEvent()
    object EditDataItem : HomeScreenEvent()
    data class SearchFocusChanged(val focusState: FocusState) : HomeScreenEvent()
    data class SearchItemEntered(val searchItem: String) : HomeScreenEvent()
}
