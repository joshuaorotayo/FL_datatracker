package com.jorotayo.fl_datatracker.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
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
    val useSystemDarkLightMode =
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
            SettingEvent.DataFieldSettings -> onDataFieldSettings()
            SettingEvent.DisplaySettings -> onDisplaySettings()
            SettingEvent.FAQsList -> onFAQsList()
        }

    }

    fun onDataFieldSettings() {
        viewModelScope.launch {
            _navigationEvent.emit(SettingNavigation.DataFieldSettings)
        }
    }

    fun onDisplaySettings() {
        viewModelScope.launch {
            _navigationEvent.emit(SettingNavigation.DisplaySettings)
        }
    }

    fun onFAQsList() {
        viewModelScope.launch {
            _navigationEvent.emit(SettingNavigation.FAQsList)
        }
    }

    sealed class SettingNavigation {
        object DataFieldSettings : SettingNavigation()
        object DisplaySettings : SettingNavigation()
        object FAQsList : SettingNavigation()
    }
}
