package com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings

import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.repository.SettingsRepository

class GetSettingByValue(
    private val repository: SettingsRepository,
) {
    operator fun invoke(settingValue: String): List<Setting> {
        return repository.getSettingByValue(settingValue)
    }
}