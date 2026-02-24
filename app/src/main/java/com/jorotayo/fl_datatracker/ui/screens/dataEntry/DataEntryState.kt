package com.jorotayo.fl_datatracker.ui.screens.dataEntry

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState

data class DataEntryState(
    val preset: Preset? = null,
    val fields: List<DataFieldUiState> = emptyList(),
    val values: Map<Long, String> = emptyMap(), // fieldId → raw string value
    val errors: Map<Long, String> = emptyMap(), // fieldId → validation message
    val editingRecordId: Long? = null, // null = new record, non-null = editing existing
    val isSaved: Boolean = false,
    val isLoading: Boolean = false
)
