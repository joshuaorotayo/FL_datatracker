package com.jorotayo.fl_datatracker.domain.model

import com.jorotayo.fl_datatracker.domain.model.converters.MemberConverter
import com.jorotayo.fl_datatracker.domain.model.converters.SontaConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class SontaHead(
    @Id
    var ministryId: Long = 0,
    @Convert(converter = SontaConverter::class, dbType = Int::class)
    var sonta: Sonta,
    @Convert(converter = MemberConverter::class, dbType = String::class)
    var sontaHead: Member
)
