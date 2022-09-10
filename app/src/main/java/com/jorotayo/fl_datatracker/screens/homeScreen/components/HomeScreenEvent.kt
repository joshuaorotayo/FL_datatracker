package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.ui.focus.FocusState
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.TestRowItem

sealed class HomeScreenEvent {
    object ResetSearchBar : HomeScreenEvent()
    object ToggleSearchBar : HomeScreenEvent()
    object ShowSettingsView : HomeScreenEvent()
    object EditDataItem : HomeScreenEvent()
    object DeleteDataItem : HomeScreenEvent()

    data class ToggleDeleteDataDialog(val dataItem: Data) : HomeScreenEvent()
    data class UpdateData(val operation: String, val testRowItem: TestRowItem) : HomeScreenEvent()
    data class SearchFocusChanged(val focusState: FocusState) : HomeScreenEvent()
    data class SearchItemEntered(val searchItem: String) : HomeScreenEvent()
}
