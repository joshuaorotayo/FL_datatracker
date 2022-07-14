package com.jorotayo.fl_datatracker.domain.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Settings(
    @Id
    var Id: Long,
    var settingName: String = "",
    var settingValue: String = ""
)
