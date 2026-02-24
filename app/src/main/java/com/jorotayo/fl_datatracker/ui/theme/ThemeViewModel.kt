package com.jorotayo.fl_datatracker.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val userPreferenceStore: UserPreferenceStore
) : ViewModel() {

    val useDeviceDarkMode: StateFlow<Boolean> = userPreferenceStore
        .getBooleanFlow(SettingsKeys.USE_DEVICE_DARK_MODE_SETTINGS)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = true
        )
}