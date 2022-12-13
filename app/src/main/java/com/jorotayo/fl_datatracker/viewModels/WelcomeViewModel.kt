package com.jorotayo.fl_datatracker.viewModels

import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.domain.useCases.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases,
) : ViewModel() {

    private val isOnBoardingComplete = settingsUseCases.getSettingByName("isOnBoardingComplete")

    fun saveOnBoardingState() {
        isOnBoardingComplete.settingBoolValue = isOnBoardingComplete.settingBoolValue != true
        settingsUseCases.addSetting(isOnBoardingComplete)
    }
}
