package com.jorotayo.fl_datatracker.ui.screens.home

import com.jorotayo.fl_datatracker.data.model.DataRecord

data class HomeScreenState(
    val isLoading: Boolean = false,
    val currentPresetId: Long = -1L,
    val records: List<DataRecord> = emptyList(),
    val filteredRecords: List<DataRecord> = emptyList(),
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
    val selectedRecord: DataRecord? = null,
    val recordToDelete: DataRecord? = null,
    val error: String? = null,
    val showDeleteDialog: Boolean
)
