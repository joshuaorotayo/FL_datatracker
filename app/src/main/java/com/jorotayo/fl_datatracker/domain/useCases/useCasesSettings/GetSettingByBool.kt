package com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings

import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.repository.SettingsRepository

class GetSettingByBool(
    private val repository: SettingsRepository,
) {
    operator fun invoke(settingBool: Boolean): List<Setting> {
        return repository.getSettingsByBool(settingBool)
    }
}