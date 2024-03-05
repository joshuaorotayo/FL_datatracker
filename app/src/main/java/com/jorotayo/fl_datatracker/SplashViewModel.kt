package com.jorotayo.fl_datatracker

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys.ONBOARDING_COMPLETE
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import com.jorotayo.fl_datatracker.navigation.MainScreens
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val userPreferenceStore: UserPreferenceStore
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)

    private val _startDestination: MutableState<String> = mutableStateOf(MainScreens.Welcome.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            if (userPreferenceStore.getBoolean(ONBOARDING_COMPLETE)) {
                _startDestination.value = MainScreens.HomeMainScreens.route
            } else {
                _startDestination.value = MainScreens.Welcome.route
            }
        }
        _isLoading.value = false
    }
}
