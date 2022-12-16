package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.domain.model.Setting

interface SettingsRepository {

    fun getSettingsList(): List<Setting>

    fun getSettingById(settingId: Long): Setting

    fun getSettingByName(settingName: String): Setting

    fun getSettingsByBool(settingBool: Boolean): List<Setting>

    fun getSettingByValue(settingValue: String): List<Setting>

    fun addSetting(setting: Setting)

    fun deleteSetting(setting: Setting)
}