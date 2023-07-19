package com.jorotayo.fl_datatracker.domain.model

import io.objectbox.annotation.ConflictStrategy
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
data class Preset(
    @Id
    var presetId: Long = 0,
    @Unique(onConflict = ConflictStrategy.FAIL)
    var presetName: String,
)

class InvalidPresetException(message: String) : Exception(message)
