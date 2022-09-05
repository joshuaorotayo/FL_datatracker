package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Setting
import io.objectbox.Box

data class BoxState(
    var _dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java),
    var _settingsBox: Box<Setting> = ObjectBox.get().boxFor(Setting::class.java),
    var _presetBox: Box<Preset> = ObjectBox.get().boxFor(Preset::class.java),

    var dataFieldsBox: MutableState<List<DataField>> = mutableStateOf(_dataFieldsBox.all.toList()),
    var settingsBox: MutableState<List<Setting>> = mutableStateOf(_settingsBox.all.toList()),
    var presetBox: MutableState<List<Preset>> = mutableStateOf(_presetBox.all.toList()),

    )