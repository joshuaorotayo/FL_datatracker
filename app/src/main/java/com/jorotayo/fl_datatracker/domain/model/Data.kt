package com.jorotayo.fl_datatracker.domain.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Data(
    @Id
    var id: Long = 0,
    var name: String = "",
    var dataFields: List<DataField> = listOf(),
    var lastEdited: String = "",
)