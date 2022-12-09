package com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings

import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.repository.SettingsRepository

class AddSetting(
    private val repository: SettingsRepository,
) {
    operator fun invoke(setting: Setting) {
        return repository.addSetting(setting)
    }
}