package com.jorotayo.fl_datatracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.usecase.DeleteRecordUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetAllRecordsUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetFieldsForPresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetPresetsUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetRecordEntriesUseCase
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
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
    private val deleteRecord: DeleteRecordUseCase,
    private val getFieldsForPreset: GetFieldsForPresetUseCase,
    private val getPresets: GetPresetsUseCase,
    private val userPreferenceStore: UserPreferenceStore
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState(showDeleteDialog = true))
    val state = _state.asStateFlow()

    init {
        loadRecords()
        observeCurrentPreset()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is DeleteRecord -> onDeleteRecord()
            is SelectRecord -> onSelectRecord(event)
            is NavigateToEntry -> onNavigateToEntry(event)
            is SearchQueryChanged -> onSearchQueryChanged(event)
            is RequestDeleteRecord -> onRequestDeleteRecord(event)
            is ClearSearch -> onClearSearch()
            is ToggleSearch -> onToggleSearch()
            is DismissDeleteDialog -> onDismissDeleteDialog()
            is DismissToast -> onDismissToast()
        }
    }

    private fun loadRecords() {
        _state.update { it.copy(isLoading = true) }
        getAllRecords.asFlow()
            .onEach { records ->
                _state.update { s ->
                    s.copy(
                        isLoading = false,
                        records = records,
                        filteredRecords = if (s.searchQuery.isBlank()) {
                            records
                        } else {
                            records.filter {
                                it.title.contains(s.searchQuery, ignoreCase = true)
                            }
                        }
                    )
                }
            }
            .catch { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeCurrentPreset() {
        userPreferenceStore.getStringFlow(SettingsKeys.CURRENT_PRESET)
            .onEach { savedId ->
                val resolvedId = savedId.toLongOrNull()
                    ?: getPresets().firstOrNull {
                        it.presetName.equals(
                            "default",
                            ignoreCase = true
                        )
                    }?.presetId
                    ?: getPresets().firstOrNull()?.presetId
                    ?: -1L  // only reachable if the DB is completely empty
                _state.update { it.copy(currentPresetId = resolvedId) }
            }
            .launchIn(viewModelScope)
    }
    private fun onDeleteRecord() {
        deleteRecord.invoke(state.value.recordToDelete!!.recordId)
        // Hide the confirmation dialog once delete is committed
        _state.update { it.copy(recordToDelete = null, showDeleteDialog = false) }
    }

    private fun onSelectRecord(event: SelectRecord) {
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
    private fun onNavigateToEntry(event: NavigateToEntry) {
        val presetId = event.presetId
            ?.takeIf { it != -1L }
            ?: state.value.currentPresetId

        val fields = getFieldsForPreset(presetId)

        if (fields.isEmpty()) {
            _state.update {
                it.copy(
                    toast = AppToastData(
                        message = "\"${getPresetName(presetId)}\" has no data fields yet.",
                        mode = ToastMode.WARNING,
                        actionLabel = "Add fields →",
                        onAction = {
                            navigationManager.navigate(NavCommand.ToRoute(Screen.DataForm.route))
                            onDismissToast()
                        },
                        durationMs = 6000L
                    )
                )
            }
        } else {
            navigationManager.navigate(NavCommand.ToRoute(Screen.DataEntry.route(presetId)))
        }
    }

    private fun getPresetName(presetId: Long): String =
        getPresets().firstOrNull { it.presetId == presetId }?.presetName ?: "This preset"

    /**
     * Filters [HomeScreenState.records] against [HomeScreenState.searchQuery] (case-insensitive title
     * match) and updates [HomeScreenState.filteredRecords] on every keystroke.
     */
    private fun onSearchQueryChanged(event: SearchQueryChanged) {
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
    private fun onRequestDeleteRecord(event: RequestDeleteRecord) {
        _state.update { it.copy(recordToDelete = event.record, showDeleteDialog = true) }
    }

    /**
     * Dismisses the delete confirmation dialog without deleting anything.
     */
    private fun onDismissDeleteDialog() {
        _state.update { it.copy(recordToDelete = null, showDeleteDialog = false) }
    }

    // ── Private helpers ───────────────────────────────────────────────────────


    private fun onDismissToast() {
        _state.update { it.copy(toast = null) }

    }
}
