package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.useCases.PresetUseCases
import com.jorotayo.fl_datatracker.domain.useCases.SettingsUseCases
import com.jorotayo.fl_datatracker.navigation.Screen
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases,
    private val presetUseCases: PresetUseCases,
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Welcome.route)
    val startDestination: State<String> = _startDestination

    private val isOnBoardingComplete = settingsUseCases.getSettingByName("isOnBoardingComplete")

    init {
        viewModelScope.launch {
            if (isOnBoardingComplete.settingBoolValue == true) {
                _startDestination.value = Screen.HomeScreen.route
            } else {
                _startDestination.value = Screen.Welcome.route
            }
        }
        _isLoading.value = false
    }
}
