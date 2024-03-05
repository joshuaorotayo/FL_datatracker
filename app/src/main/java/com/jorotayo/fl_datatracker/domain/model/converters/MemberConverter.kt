package com.jorotayo.fl_datatracker.domain.model.converters

import com.google.gson.Gson
import com.jorotayo.fl_datatracker.domain.model.Member
import io.objectbox.converter.PropertyConverter

class MemberConverter : PropertyConverter<Member, String> {
    override fun convertToEntityProperty(databaseValue: String): Member {
        val gson = Gson()
        return gson.fromJson(databaseValue, Member::class.java)
    }

    override fun convertToDatabaseValue(entityProperty: Member): String {
        val gson = Gson()
        return gson.toJson(entityProperty)
    }
}