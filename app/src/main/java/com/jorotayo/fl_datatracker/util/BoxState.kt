package com.jorotayo.fl_datatracker.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.*
import io.objectbox.Box
import io.objectbox.query.Query

data class BoxState(

    val _dataBox: Box<Data> = ObjectBox.get().boxFor(Data::class.java),
    val dataBox: List<Data> = _dataBox.all.toList(),

    val _dataItemBox: Box<DataItem> = ObjectBox.get().boxFor(DataItem::class.java),
    val dataItemBox: List<DataItem> = _dataItemBox.all.toList(),

    val _dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java),
    var dataFieldsBox: MutableState<Box<DataField>> = mutableStateOf(ObjectBox.get()
        .boxFor(DataField::class.java)),
    var dataFieldsList: List<DataField> = _dataFieldsBox.all.toList(),

    val _settingsBox: Box<Setting> = ObjectBox.get().boxFor(Setting::class.java),
    val settingsBox: List<Setting> = _settingsBox.all.toList(),

    val _presetsBox: Box<Preset> = ObjectBox.get().boxFor(Preset::class.java),
    val presetsBox: List<Preset> = _presetsBox.all.toList(),

    val currentPresetSetting: Setting? = _settingsBox.query(Setting_.settingName.equal("currentPreset"))
        .build().findFirst(),

    val currentPreset: Preset? = _presetsBox.query(Preset_.presetName.equal(currentPresetSetting?.settingStringValue!!))
        .build().findFirst(),

    val queryFiltered: Query<DataField>? =
        currentPreset?.presetId?.let {
            _dataFieldsBox.query().equal(DataField_.presetId,
                it).build()
        },

    val filteredFields: List<DataField> = queryFiltered?.find()?.toList() as List<DataField>,
)