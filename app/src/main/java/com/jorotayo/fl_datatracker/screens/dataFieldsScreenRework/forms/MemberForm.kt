package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.forms

import com.jorotayo.fl_datatracker.domain.model.Gender
import com.jorotayo.fl_datatracker.domain.model.MemberStatus
import com.jorotayo.fl_datatracker.domain.model.Sonta

data class MemberForm(
    val memberId: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val gender: Gender,
    val dob: String = "",
    val age: String = "",
    val addressLine1: String = "",
    val addressLine2: String = "",
    val town: String = "",
    val postcode: String = "",
    val memberStatus: MemberStatus = MemberStatus.DEER,
    val sonta: Sonta = Sonta.NO_MINISTRY,
    val bacentaLeader: Boolean = false,
    val constituencyOverseer: Boolean = false
)
