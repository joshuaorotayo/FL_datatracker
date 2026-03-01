package com.jorotayo.fl_datatracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.data.repository.RecordRepository
import com.jorotayo.fl_datatracker.domain.usecase.GetFieldsForPresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetSelectedPresetUseCase
import com.jorotayo.fl_datatracker.navigation.NavCommand
import com.jorotayo.fl_datatracker.navigation.NavigationManager
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData
import com.jorotayo.fl_datatracker.ui.components.toasts.ToastMode
import com.jorotayo.fl_datatracker.ui.screens.home.HomeEvent.ClearSearch
import com.jorotayo.fl_datatracker.ui.screens.home.HomeEvent.DeleteRecord
import com.jorotayo.fl_datatracker.ui.screens.home.HomeEvent.DismissDeleteDialog
import com.jorotayo.fl_datatracker.ui.screens.home.HomeEvent.DismissToast
import com.jorotayo.fl_datatracker.ui.screens.home.HomeEvent.NavigateToEntry
import com.jorotayo.fl_datatracker.ui.screens.home.HomeEvent.RequestDeleteRecord
import com.jorotayo.fl_datatracker.ui.screens.home.HomeEvent.SearchQueryChanged
import com.jorotayo.fl_datatracker.ui.screens.home.HomeEvent.SelectRecord
import com.jorotayo.fl_datatracker.ui.screens.home.HomeEvent.ToggleSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val getSelectedPreset: GetSelectedPresetUseCase,
    private val getFieldsForPreset: GetFieldsForPresetUseCase,
    val navigationManager: NavigationManager
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState(showDeleteDialog = false))
    val state = _state.asStateFlow()

    init {
        observeRecords()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is SearchQueryChanged -> onSearchQueryChanged(event.query)
            is SelectRecord -> onSelectRecord(event.record)
            is DeleteRecord -> onDeleteRecord(event.record)
            is RequestDeleteRecord -> onRequestDeleteRecord(event.record)
            is NavigateToEntry -> onNavigateToEntry()
            ToggleSearch -> onToggleSearch()
            ClearSearch -> onClearSearch()
            DismissDeleteDialog -> onDismissDeleteDialog()
            DismissToast -> onDismissToast()
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Records
    // ─────────────────────────────────────────────────────────────────────────

    private fun observeRecords() {
        recordRepository.observeAllRecords()
            .onEach { records ->
                _state.update { s ->
                    s.copy(
                        records = records,
                        filteredRecords = records.applyFilter(s.searchQuery)
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Navigation
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * FAB / bottom nav tap — guards navigation with two checks before proceeding:
     *  1. A preset must be selected (falls back to Default, so this is a safety net)
     *  2. That preset must have at least one active field defined
     *
     * Shows a specific error toast for each failure case instead of navigating.
     */
    private fun onNavigateToEntry() {
        viewModelScope.launch {
            val preset = getSelectedPreset()

            if (preset == null) {
                _state.update {
                    it.copy(
                        toast = AppToastData(
                            message = "No preset selected. Please set one up in Data Forms first.",
                            mode = ToastMode.ERROR
                        )
                    )
                }
                return@launch
            }

            val fields = getFieldsForPreset(preset.presetId)
            val activeFields = fields.filter { it.isActive }

            if (activeFields.isEmpty()) {
                _state.update {
                    it.copy(
                        toast = AppToastData(
                            message = "\"${preset.presetName}\" has no fields. Add fields in Data Forms before creating a record.",
                            mode = ToastMode.ERROR
                        )
                    )
                }
                return@launch
            }

            navigationManager.navigate(NavCommand.ToRoute(Screen.DataEntry.newRoute()))
        }
    }

    /**
     * Record card edit tap — navigate to edit mode with the record's id.
     */
    private fun onSelectRecord(record: com.jorotayo.fl_datatracker.data.model.DataRecord) {
        navigationManager.navigate(
            NavCommand.ToRoute(Screen.DataEntry.editRoute(record.recordId))
        )
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Delete
    // ─────────────────────────────────────────────────────────────────────────

    private fun onRequestDeleteRecord(record: com.jorotayo.fl_datatracker.data.model.DataRecord) {
        _state.update { it.copy(recordToDelete = record, showDeleteDialog = true) }
    }

    private fun onDeleteRecord(record: com.jorotayo.fl_datatracker.data.model.DataRecord) {
        viewModelScope.launch {
            recordRepository.deleteRecord(record.recordId)
            _state.update {
                it.copy(
                    recordToDelete = null,
                    showDeleteDialog = false,
                    toast = AppToastData(
                        message = "\"${record.title.ifBlank { "Record" }}\" deleted.",
                        mode = ToastMode.INFO
                    )
                )
            }
        }
    }

    private fun onDismissDeleteDialog() {
        _state.update { it.copy(recordToDelete = null, showDeleteDialog = false) }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Search
    // ─────────────────────────────────────────────────────────────────────────

    private fun onSearchQueryChanged(query: String) {
        _state.update { s ->
            s.copy(
                searchQuery = query,
                filteredRecords = s.records.applyFilter(query)
            )
        }
    }

    private fun onToggleSearch() {
        _state.update { s ->
            val next = !s.isSearchActive
            s.copy(
                isSearchActive = next,
                searchQuery = if (!next) "" else s.searchQuery,
                filteredRecords = if (!next) s.records else s.filteredRecords
            )
        }
    }

    private fun onClearSearch() {
        _state.update { s ->
            s.copy(searchQuery = "", filteredRecords = s.records)
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Toast
    // ─────────────────────────────────────────────────────────────────────────

    private fun onDismissToast() {
        _state.update { it.copy(toast = null) }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    private fun List<com.jorotayo.fl_datatracker.data.model.DataRecord>.applyFilter(
        query: String
    ) = if (query.isBlank()) this
    else filter { it.title.contains(query, ignoreCase = true) }
}