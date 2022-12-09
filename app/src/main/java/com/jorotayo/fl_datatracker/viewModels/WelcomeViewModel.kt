package com.jorotayo.fl_datatracker.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.useCases.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases,
) : ViewModel() {

    private val isOnBoardingComplete = settingsUseCases.getSettingByName("isOnBoardingComplete")

    fun saveOnBoardingState() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isOnBoardingComplete!!.settingBoolValue == true) {
                isOnBoardingComplete.settingBoolValue = false
                Log.i(TAG,
                    "true launch: saveOnBoardingState: " + isOnBoardingComplete.settingBoolValue)
            } else {
                isOnBoardingComplete.settingBoolValue = true

            }
            settingsUseCases.addSetting(isOnBoardingComplete)
        }
    }
}
