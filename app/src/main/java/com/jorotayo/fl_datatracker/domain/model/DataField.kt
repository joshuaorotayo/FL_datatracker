package com.jorotayo.fl_datatracker.domain.model

import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import io.objectbox.annotation.ConflictStrategy
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
data class DataField(
    @Id
    var dataFieldId: Long,
    var presetId: Long,
    @Unique(onConflict = ConflictStrategy.REPLACE)
    var fieldName: String = "",
    var dataFieldType: Int = DataFieldType.SHORT_TEXT.ordinal,
    var first: String = "",
    var second: String = "",
    var third: String = "",
    var fieldHint: String? = "Enter value for $fieldName",
    var isEnabled: Boolean = true
)

class InvalidDataFieldException(message: String) : Exception(message)
