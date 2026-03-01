package com.jorotayo.fl_datatracker.data.repository

import com.jorotayo.fl_datatracker.data.model.Preset

interface PresetRepository {
    fun getAllPresets(): List<Preset>
    fun getPresetById(presetId: Long): Preset?
    fun getDefaultPreset(): Preset?          // ← always returns the "Default" preset
    fun savePreset(preset: Preset): Long
    fun deletePreset(presetId: Long): Boolean
}