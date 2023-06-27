package com.jorotayo.fl_datatracker.domain.useCases

import com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings.AddSetting
import com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings.DeleteSetting
import com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings.GetSettingByBool
import com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings.GetSettingById
import com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings.GetSettingByName
import com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings.GetSettingByValue
import com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings.GetSettingsList

data class SettingsUseCases(
    val addSetting: AddSetting,
    val getSettingsList: GetSettingsList,
    val getSettingById: GetSettingById,
    val getSettingByValue: GetSettingByValue,
    val deleteSetting: DeleteSetting,
    val getSettingsByBool: GetSettingByBool,
    val getSettingByName: GetSettingByName,
)