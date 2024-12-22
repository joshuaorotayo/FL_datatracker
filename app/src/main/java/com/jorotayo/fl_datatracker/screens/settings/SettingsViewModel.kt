package com.jorotayo.fl_datatracker.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import com.jorotayo.fl_datatracker.screens.settings.SettingEvent.DataFieldSettings
import com.jorotayo.fl_datatracker.screens.settings.SettingEvent.DisplaySettings
import com.jorotayo.fl_datatracker.screens.settings.SettingEvent.FAQsList
import com.jorotayo.fl_datatracker.screens.settings.SettingEvent.ToggleDarkMode
import com.jorotayo.fl_datatracker.screens.settings.states.DisplayUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    userPreferenceStore: UserPreferenceStore
) : ViewModel() {

    private val useSystemDarkLightMode =
        userPreferenceStore.getBoolean(SettingsKeys.USE_DEVICE_DARK_MODE_SETTINGS)

    private val _uiState = MutableStateFlow(
        DisplayUiState(
            isSystemDarkLightEnabled = useSystemDarkLightMode,
            isLightShowing = true
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<SettingNavigation>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onSettingEvent(event: SettingEvent) {
        when (event) {
            is DataFieldSettings -> onDataFieldSettings()
            is DisplaySettings -> onDisplaySettings()
            is FAQsList -> onFAQsList()
            is ToggleDarkMode -> onToggleDarkMode()
        }
    }

    private fun onDataFieldSettings() {
        viewModelScope.launch {
            _navigationEvent.emit(SettingNavigation.DataFieldSettings)
        }
    }

    private fun onDisplaySettings() {
        viewModelScope.launch {
            _navigationEvent.emit(SettingNavigation.DisplaySettings)
        }
    }

    private fun onFAQsList() {
        viewModelScope.launch {
            _navigationEvent.emit(SettingNavigation.FAQsList)
        }
    }

    private fun onToggleDarkMode() {
        // todo
    }

    sealed class SettingNavigation {
        object DataFieldSettings : SettingNavigation()
        object DisplaySettings : SettingNavigation()
        object FAQsList : SettingNavigation()
    }
}
