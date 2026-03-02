package com.jorotayo.fl_datatracker.ui.scaffold

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ScaffoldViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(AppScaffoldState())
    val state = _state.asStateFlow()

    fun update(newState: AppScaffoldState) {
        _state.value = newState
    }
}