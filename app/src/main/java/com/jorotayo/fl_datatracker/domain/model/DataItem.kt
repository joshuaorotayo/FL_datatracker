package com.jorotayo.fl_datatracker.domain.model

import io.objectbox.annotation.Id

data class DataItem(
    @Id
    var dataItemId: Long,
    var dataId: Long,
    var dataField: DataField,
    var dataValue: String = "",
)