package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import com.jorotayo.fl_datatracker.screens.welcomeScreen.WelcomeScreenState
import com.jorotayo.fl_datatracker.screens.welcomeScreen.components.WelcomeScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val userPreferenceStore: UserPreferenceStore
) : ViewModel() {

    private var _uiState = mutableStateOf(WelcomeScreenState())
    val uiState: State<WelcomeScreenState> = _uiState

    fun onEvent(event: WelcomeScreenEvent) {
        when (event) {
            WelcomeScreenEvent.SaveOnBoarding -> saveOnBoardingState()
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
