package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.*
import io.objectbox.Box

data class BoxState(
    val _dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java),
    val dataFieldsBox: List<DataField> = _dataFieldsBox.all.toList(),

    val _settingsBox: Box<Setting> = ObjectBox.get().boxFor(Setting::class.java),
    val settingsBox: List<Setting> = _settingsBox.all.toList(),

    val _presetBox: Box<Preset> = ObjectBox.get().boxFor(Preset::class.java),
    val presetBox: List<Preset> = _presetBox.all.toList(),

    val currentPresetSetting: Setting? = _settingsBox.query(Setting_.settingName.equal("currentPreset"))
        .build().findFirst(),
    val currentPreset: Preset? = _presetBox.query(Preset_.presetName.equal(currentPresetSetting?.settingStringValue!!))
        .build().findFirst(),
)