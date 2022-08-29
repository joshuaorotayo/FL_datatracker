package com.jorotayo.fl_datatracker.domain.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Preset(
    @Id
    var presetId: Long,
    var presetName: String,
)
