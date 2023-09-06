package com.jorotayo.fl_datatracker.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.Preset
import io.objectbox.Box

data class BoxState(

    val dataBox: Box<Data> = ObjectBox.boxStore().boxFor(Data::class.java),
    val dataList: List<Data> = dataBox.all.toList(),

    val dataItemBox: Box<DataItem> = ObjectBox.boxStore().boxFor(DataItem::class.java),
    val dataItemList: List<DataItem> = dataItemBox.all.toList(),

    val dataFieldBox: Box<DataField> = ObjectBox.boxStore().boxFor(DataField::class.java),
    var dataFieldsBox: MutableState<Box<DataField>> = mutableStateOf(
        ObjectBox.boxStore()
            .boxFor(DataField::class.java)
    ),
    var dataFieldsList: List<DataField> = dataFieldBox.all.toList(),

    val presetsBox: Box<Preset> = ObjectBox.boxStore().boxFor(Preset::class.java),
    val presetsList: List<Preset> = presetsBox.all.toList()
)
