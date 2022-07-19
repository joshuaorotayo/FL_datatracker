package com.jorotayo.fl_datatracker.domain.model

import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DataField(
    @Id
    var id: Long,
    var fieldName: String = "",
    var niceFieldName: String = "",
    var dataFieldType: String = DataFieldType.SHORTSTRING.type,
    var dataValue: String = "",
    var isEnabled: Boolean = true
)