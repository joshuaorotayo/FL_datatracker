package com.jorotayo.fl_datatracker.domain.usecase

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.data.repository.PresetRepository
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import javax.inject.Inject

/**
 * Resolves the currently active preset using the following priority:
 *
 *  1. The preset id stored in [UserPreferenceStore] under [SettingsKeys.CURRENT_PRESET]
 *  2. The "Default" preset (always seeded on first install, cannot be deleted)
 *
 * Returns null only if the store is completely empty, which should never
 * happen in normal operation because [seedDefaultPreset] runs at app start.
 */
class GetSelectedPresetUseCase @Inject constructor(
    private val preferenceStore: UserPreferenceStore,
    private val presetRepository: PresetRepository
) {
    suspend operator fun invoke(): Preset? {
        // 1. Try the stored preference first
        val storedId = preferenceStore.getString(SettingsKeys.CURRENT_PRESET).toLongOrNull()
        if (storedId != null) {
            val stored = presetRepository.getPresetById(storedId)
            if (stored != null) return stored
            // Stored id is stale (preset was deleted) — fall through to default
        }

        // 2. Fall back to the Default preset
        return presetRepository.getDefaultPreset()
    }
}