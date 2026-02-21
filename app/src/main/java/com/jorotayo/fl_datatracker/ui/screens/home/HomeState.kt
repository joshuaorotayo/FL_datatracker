package com.jorotayo.fl_datatracker.ui.screens.home

import com.jorotayo.fl_datatracker.data.model.DataRecord

data class HomeState(
    val records: List<DataRecord> = emptyList(),
    val filteredRecords: List<DataRecord> = emptyList(),
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
    val selectedRecord: DataRecord? = null,
    val recordToDelete: DataRecord? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showDeleteDialog: Boolean
)
