package com.jorotayo.fl_datatracker.ui.screens.home

import com.jorotayo.fl_datatracker.data.model.DataRecord

sealed class HomeEvent {
    data class SearchQueryChanged(val query: String) : HomeEvent()
    object ToggleSearch : HomeEvent()
    object ClearSearch : HomeEvent()
    data class SelectRecord(val record: DataRecord) : HomeEvent()
    data class DeleteRecord(val record: DataRecord) : HomeEvent()
    data class NavigateToEntry(val presetId: Long) : HomeEvent()
    data class RequestDeleteRecord(val record: DataRecord) : HomeEvent()
    object DismissDeleteDialog : HomeEvent()
}
