package com.jorotayo.fl_datatracker.domain.model.converters

import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import io.objectbox.converter.PropertyConverter

class DataFieldTypeConverter : PropertyConverter<DataFieldType, Int> {
    override fun convertToEntityProperty(databaseValue: Int): DataFieldType {
        return when (databaseValue) {
            0 -> DataFieldType.SHORT_TEXT
            1 -> DataFieldType.LONG_TEXT
            2 -> DataFieldType.BOOLEAN
            3 -> DataFieldType.DATE
            4 -> DataFieldType.TIME
            5 -> DataFieldType.COUNT
            6 -> DataFieldType.TRISTATE
            7 -> DataFieldType.IMAGE
            8 -> DataFieldType.LIST
            else -> DataFieldType.SHORT_TEXT
        }
    }

    override fun convertToDatabaseValue(entityProperty: DataFieldType): Int {
        return entityProperty.value
    }
}