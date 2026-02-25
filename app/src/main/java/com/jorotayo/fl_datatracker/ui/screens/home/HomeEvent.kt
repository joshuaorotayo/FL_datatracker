package com.jorotayo.fl_datatracker.ui.screens.home

import com.jorotayo.fl_datatracker.data.model.DataRecord

sealed class HomeEvent {
    data class SearchQueryChanged(val query: String) : HomeEvent()
    data class SelectRecord(val record: DataRecord) : HomeEvent()
    data class DeleteRecord(val record: DataRecord) : HomeEvent()
    data class NavigateToEntry(val presetId: Long? = -1L) : HomeEvent()
    data class RequestDeleteRecord(val record: DataRecord) : HomeEvent()
    object ToggleSearch : HomeEvent()
    object ClearSearch : HomeEvent()
    object DismissDeleteDialog : HomeEvent()
    object DismissToast : HomeEvent()
}
