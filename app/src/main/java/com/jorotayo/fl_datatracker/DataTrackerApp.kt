package com.jorotayo.fl_datatracker

import android.app.Application
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.model.Setting_
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
        val settingBox: Box<Setting> = ObjectBox.get().boxFor(Setting::class.java)
        val presetBox: Box<Preset> = ObjectBox.get().boxFor(Preset::class.java)

        val isOnBoardingComplete =
            settingBox.query(Setting_.settingName.equal("isOnBoardingComplete")).build().findFirst()

        val currentPreset =
            settingBox.query(Setting_.settingName.equal("currentPreset")).build().findFirst()

        if (isOnBoardingComplete == null) {
            settingBox.put(
                Setting(
                    Id = 0,
                    settingName = "isOnBoardingComplete",
                    settingBoolValue = false,
                    settingStringValue = ""
                )
            )
        }
        if (currentPreset == null) {
            presetBox.put(
                Preset(
                    presetId = 0,
                    presetName = "Default"
                )
            )
            settingBox.put(
                Setting(
                    Id = 1,
                    settingName = "currentPreset",
                    settingBoolValue = false,
                    settingStringValue = "1"
                )
            )
        }
    }
}