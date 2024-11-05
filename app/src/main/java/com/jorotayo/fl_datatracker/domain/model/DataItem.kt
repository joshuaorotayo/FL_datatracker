package com.jorotayo.fl_datatracker.domain.model

import com.jorotayo.fl_datatracker.domain.model.converters.DataFieldTypeConverter
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.domain.util.DataFieldType.SHORT_TEXT
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DataItem(
    @Id
    var dataItemId: Long = 0,
    var dataId: Long,
    var presetId: Long,
    var fieldName: String = "",
    @Convert(converter = DataFieldTypeConverter::class, dbType = Int::class)
    var dataFieldType: DataFieldType = SHORT_TEXT,
    var first: String = "",
    var second: String = "",
    var third: String = "",
    var isEnabled: Boolean = true,
    var fieldDescription: String? = "Enter value for $fieldName",
    var dataValue: String = "",
)

class InvalidDataItemException(message: String) : Exception(message)
