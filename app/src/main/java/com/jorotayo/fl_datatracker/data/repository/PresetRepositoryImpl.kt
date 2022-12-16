package com.jorotayo.fl_datatracker.data.repository

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Preset_
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.model.Setting_
import com.jorotayo.fl_datatracker.domain.repository.PresetRepository
import io.objectbox.Box

class PresetRepositoryImpl : PresetRepository {

    private val presetBox: Box<Preset> = ObjectBox.get().boxFor(Preset::class.java)
    private val settingsBox: Box<Setting> = ObjectBox.get().boxFor(Setting::class.java)

    override fun getPresetList(): List<Preset> {
        return presetBox.all
    }

    override fun getPresetById(presetId: Long): Preset {
        return presetBox.get(presetId)
    }

    override fun getPresetByPresetName(presetName: String): Preset {
        return presetBox.query(Preset_.presetName.equal(presetName)).build().findFirst() ?: Preset(
            presetId = 0,
            presetName = "Default")
    }

    override fun addPreset(preset: Preset) {
        presetBox.put(preset)
    }

    override fun deletePreset(preset: Preset) {
        presetBox.remove(preset)
    }

    override fun getCurrentPresetFromSettings(presetName: String): Preset {
        var presetResult = getPresetList()[0]
        val currentPresetSetting =
            settingsBox.query(Setting_.settingName.equal("currentPreset")).build().findFirst()
        for (preset: Preset in presetBox.all) {
            if (preset.presetName == currentPresetSetting!!.settingName) {
                presetResult = preset
            }
        }
        return presetResult
    }
}