package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.ui.focus.FocusState
import com.jorotayo.fl_datatracker.domain.model.TestRowItem
import io.objectbox.Box

sealed class HomeScreenEvent {
    object ResetSearchBar : HomeScreenEvent()
    object ToggleSearchBar : HomeScreenEvent()
    object ShowSettingsView : HomeScreenEvent()
    object EditDataItem : HomeScreenEvent()

    //    data class DeleteRow(val value: TestRow) : HomeScreenEvent()
    data class UpdateData(val value: Box<TestRowItem>) : HomeScreenEvent()
    data class SearchFocusChanged(val focusState: FocusState) : HomeScreenEvent()
    data class SearchItemEntered(val searchItem: String) : HomeScreenEvent()
}
