package com.jorotayo.fl_datatracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.ui.util.SharedSettingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AppStartDestination {
    object Loading : AppStartDestination()
    data class Ready(val route: String) : AppStartDestination()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedSettingService: SharedSettingService
) : ViewModel() {

    private val _startDestination =
        MutableStateFlow<AppStartDestination>(AppStartDestination.Loading)
    val startDestination = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            sharedSettingService.initialiseValues()
//            val route = if (sharedSettingService.isOnboardingComplete()) {
//                Screen.Home.route
//            } else {
//                Screen.Onboarding.route
//            }
            val route = Screen.Home.route
            _startDestination.value = AppStartDestination.Ready(route)
        }
    }
}