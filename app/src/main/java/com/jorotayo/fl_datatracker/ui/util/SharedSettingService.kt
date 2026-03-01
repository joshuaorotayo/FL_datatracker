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
    /**
     * Seeds default preference values on first install only.
     * The guard key is USE_DEVICE_DARK_MODE_SETTINGS — if it has never been
     * written then this is a fresh install. ONBOARDING_COMPLETE is intentionally
     * NOT written here; it stays absent (false) until the user actually completes
     * onboarding, at which point [setOnboardingComplete] sets it to true.
     */
    suspend fun initialiseValues() {
        val alreadyInitialised = userPreferenceStore.getBoolean(
            SettingsKeys.USE_DEVICE_DARK_MODE_SETTINGS
        )
        if (!alreadyInitialised) {
            userPreferenceStore.setBoolean(
                SettingsKeys.USE_DEVICE_DARK_MODE_SETTINGS to true
            )
            // ONBOARDING_COMPLETE is deliberately left absent so the app
            // routes to onboarding. setOnboardingComplete() sets it to true.
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