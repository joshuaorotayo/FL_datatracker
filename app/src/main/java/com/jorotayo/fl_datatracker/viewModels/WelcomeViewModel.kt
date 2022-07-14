package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(

) : ViewModel() {

    private val _checkedState = mutableStateOf(false)
    var checkedState: State<Boolean> = _checkedState

    fun saveOnBoardingState() {
        viewModelScope.launch(Dispatchers.IO) {
            //
        }
    }

}