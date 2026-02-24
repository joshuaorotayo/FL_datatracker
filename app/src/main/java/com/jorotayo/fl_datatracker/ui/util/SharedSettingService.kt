package com.jorotayo.fl_datatracker.ui.util

import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedSettingService @Inject constructor(
    private val userPreferenceStore: UserPreferenceStore
) {
    suspend fun initialiseValues() {
        if (!userPreferenceStore.getBoolean(SettingsKeys.ONBOARDING_COMPLETE)) {
            userPreferenceStore.setBoolean(
                SettingsKeys.ONBOARDING_COMPLETE to false,
                SettingsKeys.USE_DEVICE_DARK_MODE_SETTINGS to true,
            )
            userPreferenceStore.setString(
                SettingsKeys.CURRENT_PRESET to "Default"
            )
        }
    }

    fun isOnboardingCompleteFlow(): Flow<Boolean> =
        userPreferenceStore.getBooleanFlow(SettingsKeys.ONBOARDING_COMPLETE)

    suspend fun isOnboardingComplete(): Boolean =
        userPreferenceStore.getBoolean(SettingsKeys.ONBOARDING_COMPLETE)

    suspend fun setOnboardingComplete() {
        userPreferenceStore.setBoolean(SettingsKeys.ONBOARDING_COMPLETE to true)
    }
}