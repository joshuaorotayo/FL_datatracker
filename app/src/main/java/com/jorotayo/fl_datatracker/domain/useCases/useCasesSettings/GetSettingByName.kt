package com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings

import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.repository.SettingsRepository

class GetSettingByName(
    private val repository: SettingsRepository,
) {
    operator fun invoke(settingName: String): Setting {
        return repository.getSettingByName(settingName)
    }
}