package com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings

import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.repository.SettingsRepository

class DeleteSetting(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(setting: Setting) {
        return repository.deleteSetting(setting)
    }
}