package com.jorotayo.fl_datatracker.domain.model

import io.objectbox.annotation.Id

data class Preset(
    @Id
    var presetId: Long,
    var presetName: String,
)
