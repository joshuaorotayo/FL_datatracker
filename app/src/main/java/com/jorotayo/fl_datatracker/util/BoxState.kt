package com.jorotayo.fl_datatracker.util

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.*
import io.objectbox.Box

data class BoxState(


    val _dataBox: Box<Data> = ObjectBox.get().boxFor(Data::class.java),
    val dataBox: List<Data> = _dataBox.all.toList(),

    val _dataItem: Box<DataItem> = ObjectBox.get().boxFor(DataItem::class.java),
    val dataItem: List<DataItem> = _dataItem.all.toList(),

    val _dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java),
    val dataFieldsBox: List<DataField> = _dataFieldsBox.all.toList(),

    val _settingsBox: Box<Setting> = ObjectBox.get().boxFor(Setting::class.java),
    val settingsBox: List<Setting> = _settingsBox.all.toList(),

    val _presetsBox: Box<Preset> = ObjectBox.get().boxFor(Preset::class.java),
    val presetsBox: List<Preset> = _presetsBox.all.toList(),

    val currentPresetSetting: Setting? = _settingsBox.query(Setting_.settingName.equal("currentPreset"))
        .build().findFirst(),

    val currentPreset: Preset? = _presetsBox.query(Preset_.presetName.equal(currentPresetSetting?.settingStringValue!!))
        .build().findFirst(),
)