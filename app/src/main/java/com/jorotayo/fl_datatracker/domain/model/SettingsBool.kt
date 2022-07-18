package com.jorotayo.fl_datatracker.domain.model

import io.objectbox.annotation.ConflictStrategy.REPLACE
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
data class SettingsBool(
    @Id(assignable = true)
    var Id: Long,
    @Unique(onConflict = REPLACE)
    var settingName: String = "",
    var settingValue: Boolean = false
)