package com.jorotayo.fl_datatracker.domain.model

import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import io.objectbox.annotation.ConflictStrategy
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
data class DataField(
    @Id
    var id: Long,
    @Unique(onConflict = ConflictStrategy.REPLACE)
    var fieldName: String = "",
    var dataFieldType: Int = DataFieldType.SHORTSTRING.ordinal,
    var dataValue: String = "",
    var first: String = "",
    var second: String = "",
    var third: String = "",
    var isEnabled: Boolean = true,
    var fieldHint: String? = "Enter value for $fieldName"
)