package com.jorotayo.fl_datatracker.domain.repository

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Gender
import com.jorotayo.fl_datatracker.domain.model.Member
import com.jorotayo.fl_datatracker.domain.model.MemberStatus
import com.jorotayo.fl_datatracker.domain.model.Member_
import io.objectbox.Box
import javax.inject.Inject

class MemberRepository @Inject constructor() {
    private val membersBox: Box<Member> = ObjectBox.boxStore().boxFor(Member::class.java)

    fun getMembers(): List<Member> = membersBox.all

    fun getMemberById(memberId: Long): Member = membersBox.get(memberId)
    fun getMembersByGender(gender: Gender): List<Member> =
        membersBox.query(Member_.gender.equal(gender.value)).build().find()

    fun getMemberByStatus(status: MemberStatus): List<Member> =
        membersBox.query(Member_.gender.equal(status.status)).build().find()

    fun getMemberByName(name: String): List<Member> =
        membersBox.query(Member_.firstName.equal(name)).build().find()

    fun getMemberByFirstName(name: String): List<Member> =
        membersBox.query(Member_.firstName.equal(name)).build().find()

    fun getMemberByLastName(name: String): List<Member> =
        membersBox.query(Member_.lastName.equal(name)).build().find()

    fun getBacentaLeaders(): List<Member> =
        membersBox.query(Member_.bacentaLeader.equal(true)).build().find()

    fun getCOs(): List<Member> =
        membersBox.query(Member_.constituencyOverseer.equal(true)).build().find()

    fun getMemberNames(): List<String> =
        membersBox.query().build().property(Member_.firstName).findStrings().asList()

    fun insertMember(member: Member) = membersBox.put(member)

    fun deleteMember(member: Member) =
        membersBox.remove(member.memberID)

    fun deleteMembers(members: List<Member>) =
        membersBox.remove(members)

    fun updateMember(member: Member) = membersBox.put(member)

}