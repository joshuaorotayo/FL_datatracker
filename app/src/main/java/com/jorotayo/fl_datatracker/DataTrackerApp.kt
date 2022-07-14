package com.jorotayo.fl_datatracker

import android.app.Application
import com.jorotayo.fl_datatracker.domain.model.Settings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DataTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)

        defaultValues()
    }

    private fun defaultValues() {
        val settingBox = ObjectBox.get().boxFor(Settings::class.java)

        settingBox.put(
            Settings(
                Id = 0,
                settingName = "isOnboardingComplete",
                settingValue = "False"
            )
        )
    }
}