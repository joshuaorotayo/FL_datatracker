package com.jorotayo.fl_datatracker.domain.model

import com.jorotayo.fl_datatracker.domain.model.converters.SontaConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Member(
    @Id
    var memberID: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    @Convert(converter = GenderConverter::class, dbType = Int::class)
    var gender: Gender = Gender.MALE,
    var dob: String = "",
    var age: String = "",
    var address: String = "",
    @Convert(converter = MemberStatusConverter::class, dbType = Int::class)
    var memberStatus: MemberStatus,
    @Convert(converter = SontaConverter::class, dbType = Int::class)
    var sonta: Sonta,
    var bacentaLeader: Boolean = false,
    var constituencyOverseer: Boolean = false
)


