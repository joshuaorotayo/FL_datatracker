package com.jorotayo.fl_datatracker.data.objectbox

import com.jorotayo.fl_datatracker.data.model.Preset
import com.jorotayo.fl_datatracker.data.model.Preset_
import com.jorotayo.fl_datatracker.data.repository.PresetRepository
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObjectBoxPresetRepository @Inject constructor(
    store: BoxStore
) : PresetRepository {

    private val box: Box<Preset> = store.boxFor(Preset::class.java)

    override fun getAllPresets(): List<Preset> =
        box.all.toList()

    override fun getPresetById(presetId: Long): Preset? =
        box.get(presetId)

    /**
     * Returns the seeded "Default" preset by name.
     * Falls back to the first preset in the store if for any reason the
     * name lookup fails (should never happen in normal operation).
     */
    override fun getDefaultPreset(): Preset? =
        box.query(Preset_.presetName.equal("Default")).build().findFirst()
            ?: box.all.firstOrNull()

    override fun savePreset(preset: Preset): Long =
        box.put(preset)

    /**
     * Deletes the preset with [presetId].
     * Returns true if a record was removed, false if it didn't exist.
     * The "Default" preset is protected — attempting to delete it is a no-op
     * that returns false.
     */
    override fun deletePreset(presetId: Long): Boolean {
        val preset = box.get(presetId) ?: return false
        if (preset.presetName == "Default") return false
        return box.remove(presetId)
    }
}