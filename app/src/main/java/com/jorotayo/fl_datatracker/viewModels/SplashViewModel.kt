package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Settings
import com.jorotayo.fl_datatracker.navigation.Screen
import io.objectbox.Box
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
) : ViewModel() {

    var settingBox: Box<Settings> = ObjectBox.get().boxFor(Settings::class.java)
    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Welcome.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            if (settingBox[1].settingValue == "True") {
                _startDestination.value = Screen.Home.route
            } else {
                _startDestination.value = Screen.Welcome.route
            }
        }
    }
}