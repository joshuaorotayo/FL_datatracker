package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.domain.model.InvalidPresetException
import com.jorotayo.fl_datatracker.domain.model.Preset

interface PresetRepository {

    @Throws(InvalidPresetException::class)
    fun addPreset(preset: Preset)

    fun deletePreset(preset: Preset): Boolean

    fun getPresetList(): List<Preset>

    fun getPresetById(presetId: Long): Preset

    fun getPresetByPresetName(presetName: String): Preset

    fun getCurrentPresetFromSettings(): Preset
}
