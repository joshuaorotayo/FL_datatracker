package com.jorotayo.fl_datatracker.domain.model

import io.objectbox.annotation.ConflictStrategy
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
data class SettingsStr(
    @Id(assignable = true)
    var Id: Long,
    @Unique(onConflict = ConflictStrategy.REPLACE)
    var settingName: String = "",
    var settingValue: String = ""
)
