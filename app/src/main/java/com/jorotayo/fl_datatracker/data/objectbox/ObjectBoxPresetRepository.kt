package com.jorotayo.fl_datatracker.data.objectbox

import com.jorotayo.fl_datatracker.data.model.Preset
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

    override fun savePreset(preset: Preset): Long =
        box.put(preset)

    override fun deletePreset(presetId: Long) =
        box.remove(presetId)
}
