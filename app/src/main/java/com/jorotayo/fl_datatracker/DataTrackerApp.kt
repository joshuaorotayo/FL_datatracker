package com.jorotayo.fl_datatracker

import android.app.Application
import com.jorotayo.fl_datatracker.domain.model.SettingsBool
import com.jorotayo.fl_datatracker.domain.model.SettingsBool_
import dagger.hilt.android.HiltAndroidApp
import io.objectbox.Box

@HiltAndroidApp
class DataTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)

        defaultValues()
    }

    private fun defaultValues() {
        val settingBox: Box<SettingsBool> = ObjectBox.get().boxFor(SettingsBool::class.java)

        val query =
            settingBox.query(SettingsBool_.settingName.equal("isOnBoardingComplete")).build()

        val isOnBoardingComplete = query.findFirst()

        if (isOnBoardingComplete == null) {
            settingBox.put(
                SettingsBool(
                    Id = 0,
                    settingName = "isOnBoardingComplete",
                    settingValue = false
                )
            )
        }
    }
}