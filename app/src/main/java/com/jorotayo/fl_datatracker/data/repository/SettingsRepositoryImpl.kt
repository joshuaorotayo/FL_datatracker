package com.jorotayo.fl_datatracker.data.repository

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.model.Setting_
import com.jorotayo.fl_datatracker.domain.repository.SettingsRepository
import io.objectbox.Box

class SettingsRepositoryImpl : SettingsRepository {
    private val settingBox: Box<Setting> = ObjectBox.get().boxFor(Setting::class.java)

    override fun getSettingsList(): List<Setting> {
        return settingBox.all
    }

    override fun getSettingById(settingId: Long): Setting {
        return settingBox.get(settingId)
    }

    override fun getSettingsByBool(settingBool: Boolean): List<Setting> {
        return settingBox.query(Setting_.settingBoolValue.equal(settingBool)).build().find()
    }

    override fun getSettingByValue(settingValue: String): List<Setting> {
        return settingBox.query(Setting_.settingStringValue.equal(settingValue)).build().find()
    }

    override fun getSettingByName(settingName: String): Setting {
        return settingBox.query(Setting_.settingName.equal(settingName)).build().findFirst()
            ?: getSettingsList()[0]
    }

    override fun addSetting(setting: Setting) {
        settingBox.put(setting)
    }

    override fun editSetting(setting: Setting) {
        settingBox.put(setting)
    }

    override fun deleteSetting(setting: Setting) {
        settingBox.remove(setting)
    }
}