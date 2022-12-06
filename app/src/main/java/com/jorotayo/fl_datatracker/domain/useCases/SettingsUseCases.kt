package com.jorotayo.fl_datatracker.domain.useCases

import com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings.*

data class SettingsUseCases(
    val addSetting: AddSetting,
    val getSettingsList: GetSettingsList,
    val getSettingById: GetSettingById,
    val getSettingByValue: GetSettingByValue,
    val deleteSetting: DeleteSetting,
    val getSettingsByBool: GetSettingByBool,
    val getSettingByName: GetSettingByName,
)