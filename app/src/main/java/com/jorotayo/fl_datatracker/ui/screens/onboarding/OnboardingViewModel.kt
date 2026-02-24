package com.jorotayo.fl_datatracker.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.navigation.NavCommand
import com.jorotayo.fl_datatracker.navigation.NavigationManager
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.ui.util.SharedSettingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val sharedSettingService: SharedSettingService,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private var _state = MutableStateFlow(OnboardingScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.GetStarted -> onGetStarted()
            OnboardingEvent.SaveOnBoarding -> onSaveOnBoarding()
            OnboardingEvent.ToggleDontShowAgain -> onToggleDontShowAgain()
        }
    }

    private fun onGetStarted() {
        viewModelScope.launch {
            if (_state.value.dontShowAgain) sharedSettingService.setOnboardingComplete()
            navigationManager.navigate(
                NavCommand.ToRoute(
                    route = Screen.Home.route,
                    popUpTo = Screen.Onboarding.route,
                    inclusive = true
                )
            )
        }
    }

    private fun onSaveOnBoarding() {
        viewModelScope.launch {
            sharedSettingService.setOnboardingComplete()
        }
    }

    private fun onToggleDontShowAgain() {
        _state.value = _state.value.copy(
            dontShowAgain = !_state.value.dontShowAgain
        )
    }
}
