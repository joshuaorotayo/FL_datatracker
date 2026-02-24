package com.jorotayo.fl_datatracker.data.model

import com.jorotayo.fl_datatracker.data.model.converters.DataFieldTypeConverter
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.domain.util.DataFieldType.SHORT_TEXT
import io.objectbox.annotation.ConflictStrategy
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
data class DataField(
    @Id
    var dataFieldId: Long = 0L,
    var presetId: Long,
    @Unique(onConflict = ConflictStrategy.REPLACE)
    var fieldName: String = "",
    @Convert(converter = DataFieldTypeConverter::class, dbType = Int::class)
    var dataFieldType: DataFieldType = SHORT_TEXT,
    var first: String = "",
    var second: String = "",
    var third: String = "",
    var fieldHint: String? = "Enter value for $fieldName",
    var isEnabled: Boolean = true,
    var isActive: Boolean = true
)
