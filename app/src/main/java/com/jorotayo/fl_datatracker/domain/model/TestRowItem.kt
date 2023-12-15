package com.jorotayo.fl_datatracker.domain.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class TestRowItem(
    @Id
    var testRowId: Long = 0,
)
