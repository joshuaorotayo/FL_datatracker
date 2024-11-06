package com.jorotayo.fl_datatracker.domain.repositoryImpl

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.InvalidPresetException
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Preset_
import com.jorotayo.fl_datatracker.domain.repository.PresetRepository
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
import io.objectbox.Box
import javax.inject.Inject

class PresetRepositoryImpl @Inject constructor() : PresetRepository {
    @Inject
    lateinit var userPreferenceStore: UserPreferenceStore

    private val presetBox: Box<Preset> = ObjectBox.boxStore().boxFor(Preset::class.java)

    @Throws(InvalidPresetException::class)
    override fun addPreset(preset: Preset) {
        val results =
            presetBox.query(Preset_.presetName.equal(preset.presetName)).build().find()

        if (results.size != 0) {
            throw InvalidPresetException("Preset name: (${preset.presetName}) already exists")
        } else {
            presetBox.put(preset)
        }
    }

    override fun getPresetList(): List<Preset> =
        presetBox.all

    override fun getPresetById(presetId: Long): Preset =
        presetBox.get(presetId)

    override fun getPresetByPresetName(presetName: String): Preset =
        presetBox.query(Preset_.presetName.equal(presetName)).build().findFirst() ?: Preset(
            presetId = 0,
            presetName = "Default"
        )

    override fun deletePreset(preset: Preset) =
        presetBox.remove(preset)

    override fun getCurrentPresetFromSettings(): Preset {
        var presetResult = getPresetList()[0]
        val currentPresetName =
            userPreferenceStore.getString(SettingsKeys.CURRENT_PRESET) ?: "Default"
        for (preset: Preset in presetBox.all) {
            if (preset.presetName == currentPresetName) {
                presetResult = preset
            }
        }
        return presetResult
    }
}