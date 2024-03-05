package com.jorotayo.fl_datatracker.domain.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Constituency(
    @Id
    var constituencyID: Long = 0,
    var overseer: Long = 0,
    var constituencyName: String = "",
    var bacentaLeaders: List<Member> = listOf()
)
