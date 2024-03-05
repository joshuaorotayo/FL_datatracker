package com.jorotayo.fl_datatracker.screens.onboarding

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import com.jorotayo.fl_datatracker.screens.onboarding.components.OnboardingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPreferenceStore: UserPreferenceStore
) : ViewModel() {

    private var _uiState = mutableStateOf(OnboardingScreenState())
    val uiState: State<OnboardingScreenState> = _uiState

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.SaveOnBoarding -> saveOnBoardingState()
        }
    }

    private val isOnBoardingComplete =
        userPreferenceStore.getBoolean(SettingsKeys.ONBOARDING_COMPLETE)

    private fun saveOnBoardingState() {
        userPreferenceStore.setBoolean(
            Pair(
                SettingsKeys.ONBOARDING_COMPLETE,
                isOnBoardingComplete.not()
            )
        )
    }
}
