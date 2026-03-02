package com.jorotayo.fl_datatracker.ui.screens.dataEntry

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData

enum class DataEntryMode { NEW, EDIT }

data class DataEntryState(
    val mode: DataEntryMode = DataEntryMode.NEW,
    val preset: Preset? = null,
    val presetMissing: Boolean = false,
    val fields: List<DataFieldUiState> = emptyList(),

    // ── Record name ───────────────────────────────────────────────────────────
    /** The name the user types at the top of the form, saved as DataRecord.title */
    val recordName: String = "",

    val values: Map<Long, String> = emptyMap(),
    val errors: Map<Long, String> = emptyMap(),
    val isReadOnly: Boolean = false,
    val editingRecordId: Long? = null,
    val isSaved: Boolean = false,
    val isLoading: Boolean = false,
    val toast: AppToastData? = null
)