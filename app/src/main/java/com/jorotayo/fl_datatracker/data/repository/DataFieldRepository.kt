package com.jorotayo.fl_datatracker.data.repository

import com.jorotayo.fl_datatracker.data.model.DataField

interface DataFieldRepository {
    fun getFieldsForPreset(presetId: Long): List<DataField>
    fun saveField(field: DataField): Long
    fun deleteField(fieldId: Long): Boolean
}
