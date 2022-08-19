package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreenState
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

/*    private val _searchFieldState = mutableStateOf(
        TextFieldState()
    )
    val searchFieldState: State<TextFieldState> = _searchFieldState

    private val _searchBarState = mutableStateOf(SearchBarState())
    val searchBarState: State<SearchBarState> = _searchBarState

    private val _dataItems = mutableStateOf(6) // data items Value
    val dataItems: State<Int> = _dataItems*/

    private var _uiState = mutableStateOf(
        HomeScreenState(
            isSearchVisible = false,
            text = "",
            hint = "Search for data",
            isHintVisible = true,
            dataItems = ObjectBox.get().boxFor(Data::class.java).all

        )
    )
    val uiState: State<HomeScreenState> = _uiState

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.ResetSearchBar -> {
                _uiState.value = uiState.value.copy(
                    text = ""
                )
            }
            is HomeScreenEvent.SearchItemEntered -> {
                _uiState.value = uiState.value.copy(
                    text = event.searchItem
                )
            }
            is HomeScreenEvent.ToggleSearchBar -> {
                _uiState.value = uiState.value.copy(
                    isSearchVisible = !uiState.value.isSearchVisible
                )
            }
            is HomeScreenEvent.SearchFocusChanged -> {
                _uiState.value = uiState.value.copy(
                    isHintVisible = !event.focusState.isFocused
                )
            }
            HomeScreenEvent.EditDataItem -> TODO()
        }
    }
}