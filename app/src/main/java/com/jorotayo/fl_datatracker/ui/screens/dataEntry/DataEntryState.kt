package com.jorotayo.fl_datatracker.ui.screens.dataEntry

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData

enum class DataEntryMode { NEW, EDIT }

data class DataEntryState(
    // ── Mode ────────────────────────────────────────────────────────────────
    val mode: DataEntryMode = DataEntryMode.NEW,

    // ── Preset / fields ─────────────────────────────────────────────────────
    val preset: Preset? = null,
    /**
     * True when opening a record whose preset has since been deleted.
     * The record data is preserved but the form cannot be edited.
     */
    val presetMissing: Boolean = false,
    val fields: List<DataFieldUiState> = emptyList(),

    // ── Values & validation ──────────────────────────────────────────────────
    /** fieldId → raw string value */
    val values: Map<Long, String> = emptyMap(),
    /** fieldId → validation error message (shown inline under each field) */
    val errors: Map<Long, String> = emptyMap(),

    // ── Read-only gate ───────────────────────────────────────────────────────
    /** EDIT mode always starts read-only; user must tap Edit to unlock. */
    val isReadOnly: Boolean = false,

    // ── Identity ─────────────────────────────────────────────────────────────
    /** null = new record, non-null = editing an existing one */
    val editingRecordId: Long? = null,

    // ── UI flags ─────────────────────────────────────────────────────────────
    val isSaved: Boolean = false,
    val isLoading: Boolean = false,
    val toast: AppToastData? = null
)