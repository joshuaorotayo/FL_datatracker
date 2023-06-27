package com.jorotayo.fl_datatracker.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataField_
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Preset_
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.model.Setting_
import io.objectbox.Box
import io.objectbox.query.Query

data class BoxState(

    val dataBox: Box<Data> = ObjectBox.get().boxFor(Data::class.java),
    val dataList: List<Data> = dataBox.all.toList(),

    val dataItemBox: Box<DataItem> = ObjectBox.get().boxFor(DataItem::class.java),
    val dataItemList: List<DataItem> = dataItemBox.all.toList(),

    val dataFieldBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java),
    var dataFieldsBox: MutableState<Box<DataField>> = mutableStateOf(ObjectBox.get()
        .boxFor(DataField::class.java)),
    var dataFieldsList: List<DataField> = dataFieldBox.all.toList(),

    val settingsBox: Box<Setting> = ObjectBox.get().boxFor(Setting::class.java),
    val settingList: List<Setting> = settingsBox.all.toList(),

    val presetsBox: Box<Preset> = ObjectBox.get().boxFor(Preset::class.java),
    val presetsList: List<Preset> = presetsBox.all.toList(),

    val currentPresetSetting: Setting? = settingsBox.query(Setting_.settingName.equal("currentPreset"))
        .build().findFirst(),

    val currentPreset: Preset? = presetsBox.query(Preset_.presetName.equal(currentPresetSetting?.settingStringValue!!))
        .build().findFirst(),

    val queryFiltered: Query<DataField>? =
        currentPreset?.presetId?.let {
            dataFieldBox.query().equal(DataField_.presetId,
                it).build()
        },

    val filteredFields: List<DataField> = if (queryFiltered?.find()
            ?.toList() != null
    ) queryFiltered.find().toList() else listOf(),
)