package com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings

import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.repository.SettingsRepository

class GetSettingsList(
    private val repository: SettingsRepository,
) {
    operator fun invoke(): List<Setting> {
        return repository.getSettingsList()
    }
}