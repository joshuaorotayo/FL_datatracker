package com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings

import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class GetSettingsList(
    private val repository: SettingsRepository,
) {
    operator fun invoke(): Flow<Setting> {
        return repository.getSettingsList().asFlow()
    }
}