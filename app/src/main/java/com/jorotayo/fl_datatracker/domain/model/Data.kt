package com.jorotayo.fl_datatracker.domain.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Data(
    @Id
    var dataId: Long = 0,
    var name: String = "",
    var lastEdited: String = "",
    var dataFields: List<DataField> = listOf(),
)