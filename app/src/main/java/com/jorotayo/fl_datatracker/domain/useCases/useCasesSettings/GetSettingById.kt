package com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings

import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.repository.SettingsRepository

class GetSettingById(
    private val repository: SettingsRepository,
) {
    operator fun invoke(settingId: Long): Setting {
        return repository.getSettingById(settingId)
    }
}