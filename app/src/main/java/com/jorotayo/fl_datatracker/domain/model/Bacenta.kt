package com.jorotayo.fl_datatracker.domain.model

import com.jorotayo.fl_datatracker.domain.model.converters.MemberConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Bacenta(
    @Id
    var bacentaID: Long = 0,
    @Convert(converter = MemberConverter::class, dbType = String::class)
    var bacentaHead: Member? = null,
    var bacentaMembers: List<Member> = listOf()
)