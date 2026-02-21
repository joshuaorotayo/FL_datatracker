package com.jorotayo.fl_datatracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.usecase.DeleteRecordUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetAllRecordsUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetRecordEntriesUseCase
import com.jorotayo.fl_datatracker.navigation.NavCommand
import com.jorotayo.fl_datatracker.navigation.NavigationManager
import com.jorotayo.fl_datatracker.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    private val getAllRecords: GetAllRecordsUseCase,
    private val getEntries: GetRecordEntriesUseCase,
    private val deleteRecord: DeleteRecordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState(showDeleteDialog = true))
    val state = _state.asStateFlow()

    init {
        loadRecords()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.DeleteRecord -> onDeleteRecord()
            is HomeEvent.SelectRecord -> onSelectRecord(event)
            is HomeEvent.NavigateToEntry -> onNavigateToEntry(event)
            is HomeEvent.SearchQueryChanged -> onSearchQueryChanged(event)
            is HomeEvent.RequestDeleteRecord -> onRequestDeleteRecord(event)
            HomeEvent.ClearSearch -> onClearSearch()
            HomeEvent.ToggleSearch -> onToggleSearch()
            HomeEvent.DismissDeleteDialog -> onDismissDeleteDialog()
        }
    }

    // ── Already implemented ───────────────────────────────────────────────────

    private fun onDeleteRecord() {
        deleteRecord.invoke(state.value.recordToDelete!!.recordId)
        // Hide the confirmation dialog once the delete is committed
        _state.update { it.copy(recordToDelete = null, showDeleteDialog = false) }
    }

    private fun onSelectRecord(event: HomeEvent.SelectRecord) {
        navigationManager.navigate(NavCommand.ToRoute(Screen.DataEntry.route(event.record.recordId)))
    }

    private fun onToggleSearch() {
        val isCurrentlyActive = state.value.isSearchActive
        _state.update {
            it.copy(
                isSearchActive = !isCurrentlyActive,
                // Clear query and restore full list when closing the bar
                searchQuery = if (isCurrentlyActive) "" else it.searchQuery,
                filteredRecords = if (isCurrentlyActive) it.records else it.filteredRecords
            )
        }
    }

    // ── Newly implemented ─────────────────────────────────────────────────────

    /**
     * Navigates to a new blank entry for the given preset, or to the default
     * entry screen when no preset is specified.
     */
    private fun onNavigateToEntry(event: HomeEvent.NavigateToEntry) {
        navigationManager.navigate(NavCommand.ToRoute(Screen.DataEntry.route(event.presetId)))
    }

    /**
     * Filters [HomeState.records] against [event.query] (case-insensitive title
     * match) and updates [HomeState.filteredRecords] on every keystroke.
     */
    private fun onSearchQueryChanged(event: HomeEvent.SearchQueryChanged) {
        _state.update { s ->
            s.copy(
                searchQuery = event.query,
                filteredRecords = if (event.query.isBlank()) {
                    s.records
                } else {
                    s.records.filter {
                        it.title.contains(event.query, ignoreCase = true)
                    }
                }
            )
        }
    }

    /**
     * Clears the search query and restores the full record list while keeping
     * the search bar visible.
     */
    private fun onClearSearch() {
        _state.update { s ->
            s.copy(
                searchQuery = "",
                filteredRecords = s.records
            )
        }
    }

    /**
     * Stores the record the user wants to delete and raises the confirmation
     * dialog. The actual deletion is deferred until [onDeleteRecord] is called
     * when the user confirms.
     */
    private fun onRequestDeleteRecord(event: HomeEvent.RequestDeleteRecord) {
        _state.update { it.copy(recordToDelete = event.record, showDeleteDialog = true) }
    }

    /**
     * Dismisses the delete confirmation dialog without deleting anything.
     */
    private fun onDismissDeleteDialog() {
        _state.update { it.copy(recordToDelete = null, showDeleteDialog = false) }
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private fun loadRecords() {
        _state.update { it.copy(isLoading = true) }
        getAllRecords.asFlow()
            .onEach { records ->
                _state.update { s ->
                    s.copy(
                        isLoading = false,
                        records = records,
                        filteredRecords = if (s.searchQuery.isBlank()) records
                        else records.filter {
                            it.title.contains(s.searchQuery, ignoreCase = true)
                        }
                    )
                }
            }
            .catch { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
            .launchIn(viewModelScope)
    }
}