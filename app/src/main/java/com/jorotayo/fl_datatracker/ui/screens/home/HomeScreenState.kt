package com.jorotayo.fl_datatracker.ui.screens.home

import com.jorotayo.fl_datatracker.data.model.DataRecord
import com.jorotayo.fl_datatracker.ui.util.components.toasts.AppToastData

data class HomeScreenState(
    val records: List<DataRecord> = emptyList(),
    val filteredRecords: List<DataRecord> = emptyList(),
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
    val selectedRecord: DataRecord? = null,
    val recordToDelete: DataRecord? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showDeleteDialog: Boolean,
    val toast: AppToastData? = null
)
