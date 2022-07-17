package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(

) : ViewModel() {

    var _count = mutableStateOf(1)
    var count: State<Int> = _count


    private val _searchFieldState = mutableStateOf(
        TextFieldState()
    )
    val searchFieldState: State<TextFieldState> = _searchFieldState

    var isSearchVisible = mutableStateOf(false)

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.ResetSearchBar -> {
                _searchFieldState.value = searchFieldState.value.copy(
                    // isHintVisible = true,
                    text = ""
                )

            }
            HomeScreenEvent.IncrementCount -> {
                _count.value = count.value.inc()
            }
            HomeScreenEvent.DecrementCount -> {
                _count.value = count.value.dec()
            }
            is HomeScreenEvent.SearchItemEntered -> {
                _searchFieldState.value = searchFieldState.value.copy(
                    text = event.searchItem,
                )
            }
            is HomeScreenEvent.ToggleSearchBar -> {
                isSearchVisible.value = !isSearchVisible.value

            }
            is HomeScreenEvent.SearchFocusChanged -> {
                _searchFieldState.value = searchFieldState.value.copy(
                    isHintVisible = !event.focusState.isFocused
                )
            }
        }
    }
}