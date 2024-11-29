package com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset

import com.jorotayo.fl_datatracker.domain.model.InvalidPresetException
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Preset_
import com.jorotayo.fl_datatracker.domain.repository.PresetRepository
import com.jorotayo.fl_datatracker.util.BoxState

class AddPreset(
    private val repository: PresetRepository,
) {
    @Throws(InvalidPresetException::class)
    operator fun invoke(preset: Preset) {
        val boxState = BoxState()

        val results =
            boxState.presetsBox.query(Preset_.presetName.equal(preset.presetName)).build().find()

        if (results.size != 0) {
            throw InvalidPresetException("Preset name: (${preset.presetName}) already exists")
        } else {
            repository.addPreset(preset)
        }
    }
}
