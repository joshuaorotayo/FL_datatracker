package com.jorotayo.fl_datatracker.data.repository

import com.jorotayo.fl_datatracker.data.model.Preset

interface PresetRepository {
    fun getAllPresets(): List<Preset>
    fun savePreset(preset: Preset): Long
    fun deletePreset(presetId: Long): Boolean
}
