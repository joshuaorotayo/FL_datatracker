package com.jorotayo.fl_datatracker.data.repository

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Preset_
import com.jorotayo.fl_datatracker.domain.repository.PresetRepository
import io.objectbox.Box

class PresetRepositoryImpl : PresetRepository {

    private val presetBox: Box<Preset> = ObjectBox.get().boxFor(Preset::class.java)

    override fun getPresetList(): List<Preset> {
        return presetBox.all
    }

    override fun getPresetById(presetId: Long): Preset {
        return presetBox.get(presetId)
    }

    override fun getPresetByPresetName(presetName: String): Preset? {
        return presetBox.query(Preset_.presetName.equal(presetName)).build().findFirst()
    }

    override fun addPreset(preset: Preset) {
        presetBox.put(preset)
    }

    override fun deletePreset(preset: Preset) {
        presetBox.remove(preset)
    }
}