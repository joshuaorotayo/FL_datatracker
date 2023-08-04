package com.jorotayo.fl_datatracker.viewModels

import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import com.jorotayo.fl_datatracker.screens.settings.SettingEvent
import com.jorotayo.fl_datatracker.screens.settings.SettingEvent.DataFieldSettings
import com.jorotayo.fl_datatracker.screens.settings.SettingEvent.DisplaySettings
import com.jorotayo.fl_datatracker.screens.settings.SettingEvent.FAQsList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferenceStore: UserPreferenceStore
) : ViewModel() {

    fun onEvent(event: SettingEvent) {
        when (event) {
            DataFieldSettings -> onDataFieldSettings()
            DisplaySettings -> onDisplaySettings()
            FAQsList -> onFAQsList()
        }
    }

    private fun onDataFieldSettings() {

    }

    private fun onDisplaySettings() {
//        SettingsNavGraph()
    }

    private fun onFAQsList() {

    }
}
