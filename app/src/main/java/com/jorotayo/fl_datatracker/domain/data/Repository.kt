package com.jorotayo.fl_datatracker.domain.data

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.*

class Repository {

    private val presetBox = ObjectBox.get().boxFor(Preset::class.java)
    private val dataBox = ObjectBox.get().boxFor(Data::class.java)
    private val dataFieldBox = ObjectBox.get().boxFor(DataField::class.java)
    private val dataItemBox = ObjectBox.get().boxFor(DataItem::class.java)

    fun addPreset(preset: Preset) {
        presetBox.put(preset)
    }

    fun removePreset(preset: Preset) {
        presetBox.remove(preset)
        for (dataField in dataFieldBox.all) {
            if (dataField.presetId == preset.presetId) {
                dataFieldBox.remove(dataField)
            }
        }
    }

    fun getAllData(): List<Data> {
        return dataBox.all
    }

    fun getDataItemById(id: Long): Data {
        return dataBox.get(id)
    }

    fun getDataItemByName(name: String): MutableList<Data> {
        return dataBox.query(Data_.name.equal(name)).build().find()
    }

    fun addData(data: Data) {
        dataBox.put(data)
    }

    fun removeData(data: Data) {
        dataBox.remove(data)
    }

    fun getDataFieldNames(): Array<out String>? {
        return dataFieldBox.query().build().property(DataField_.fieldName).findStrings()
    }
}