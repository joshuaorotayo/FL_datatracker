package com.jorotayo.fl_datatracker.domain.model

import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DataItem(
    @Id
    var dataItemId: Long = 0,
    var dataId: Long,
    var presetId: Long,
    var fieldName: String = "",
    var dataFieldType: Int = DataFieldType.SHORT_TEXT.ordinal,
    var first: String = "",
    var second: String = "",
    var third: String = "",
    var isEnabled: Boolean = true,
    var fieldDescription: String? = "Enter value for $fieldName",
    var dataValue: String = "",
)