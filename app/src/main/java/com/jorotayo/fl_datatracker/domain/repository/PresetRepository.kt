package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.domain.model.Preset

interface PresetRepository {

    fun getPresetList(): List<Preset>

    fun getPresetById(presetId: Long): Preset

    fun getPresetByPresetName(presetName: String): Preset

    fun addPreset(preset: Preset)

    fun deletePreset(preset: Preset)

    fun getCurrentPresetFromSettings(presetName: String): Preset
}
