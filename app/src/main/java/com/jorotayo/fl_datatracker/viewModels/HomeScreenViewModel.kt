package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchBarState
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchVisible
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

    private val _searchFieldState = mutableStateOf(
        TextFieldState()
    )
    val searchFieldState: State<TextFieldState> = _searchFieldState

    private val _searchBarState = mutableStateOf(SearchBarState())
    val searchBarState: State<SearchBarState> = _searchBarState

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.ResetSearchBar -> {
                _searchFieldState.value = searchFieldState.value.copy(
                    // isHintVisible = true,
                    text = ""
                )
            }
            is HomeScreenEvent.SearchItemEntered -> {
                _searchFieldState.value = searchFieldState.value.copy(
                    text = event.searchItem,
                )
            }
            is HomeScreenEvent.ToggleSearchBarShow -> {
                _searchBarState.value = searchBarState.value.copy(
                    isSearchVisible = SearchVisible.SearchBarShowing
                )
            }
            is HomeScreenEvent.ToggleSearchBarHide -> {
                _searchBarState.value = searchBarState.value.copy(
                    isSearchVisible = SearchVisible.SearchBarHidden
                )
            }
            is HomeScreenEvent.SearchFocusChanged -> {
                _searchFieldState.value = searchFieldState.value.copy(
                    isHintVisible = !event.focusState.isFocused
                )
            }
            is HomeScreenEvent.ToggleSearchBar -> TODO()
        }
    }
}