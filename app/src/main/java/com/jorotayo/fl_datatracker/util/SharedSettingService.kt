package com.jorotayo.fl_datatracker.util

import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedSettingService @Inject constructor(
    private val userPreferenceStore: UserPreferenceStore
) {
    fun initialiseValues() {
        if (!userPreferenceStore.getBoolean(SettingsKeys.ONBOARDING_COMPLETE)) {
            userPreferenceStore.setBoolean(Pair(SettingsKeys.ONBOARDING_COMPLETE, false))
            userPreferenceStore.setBoolean(Pair(SettingsKeys.USE_DEVICE_DARK_MODE_SETTINGS, true))
            userPreferenceStore.setString(Pair(SettingsKeys.CURRENT_PRESET, "Default"))
        }
    }


    fun showDashboardNavBar(show: Boolean) {
        showingDashboardNavBar.postValue(show)
    }

    companion object {
        val useDeviceDarkModeSettings: MutableLiveData<Boolean> = MutableLiveData(true)
        val showingDashboardNavBar: MutableLiveData<Boolean> = MutableLiveData(true)
        val bottomSheetExpanded: MutableLiveData<Boolean> = MutableLiveData(false)
        val bottomSheetContent: @Composable() () -> Unit = { }
    }
}
