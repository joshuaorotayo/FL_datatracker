package com.jorotayo.fl_datatracker.domain.model

import io.objectbox.converter.PropertyConverter

enum class Gender(val value: Int, string: String) {
    MALE(value = 1, string = "Male"),
    FEMALE(value = 2, string = "Female")
}

class GenderConverter : PropertyConverter<Gender?, Int> {
    override fun convertToEntityProperty(databaseValue: Int?): Gender {
        return if (databaseValue == 0) {
            Gender.MALE
        } else {
            Gender.FEMALE
        }
    }

    override fun convertToDatabaseValue(entityProperty: Gender?): Int {
        return entityProperty?.value ?: 0
    }
}

enum class MemberStatus(val value: Int, val status: String) {
    SHEEP(value = 0, status = "Sheep"),
    DEER(value = 1, status = "Deer"),
    GOAT(value = 2, status = "Goat")
}

class MemberStatusConverter : PropertyConverter<MemberStatus?, Int> {
    override fun convertToEntityProperty(databaseValue: Int): MemberStatus {
        return when (databaseValue) {
            0 -> MemberStatus.SHEEP
            1 -> MemberStatus.DEER
            2 -> MemberStatus.GOAT
            else -> {
                MemberStatus.SHEEP
            }
        }
    }

    override fun convertToDatabaseValue(entityProperty: MemberStatus?): Int {
        return entityProperty?.value ?: 1
    }

}

enum class Sonta(val value: Int, val ministry: String) {
    AIRPORT_STARS(value = 0, ministry = "Airport Stars"),
    DANCING_STARS(value = 1, ministry = "Dancing Stars"),
    FILM_STARS(value = 2, ministry = "Film Stars"),
    GREATER_LOVE_GOSPEL_CHOIR(value = 3, ministry = "Greater Love Gospel Choir"),
    INSTRUMENTALISTS(value = 4, ministry = "Instrumentalists"),
    LIGHTS_TEAM(value = 5, ministry = "Lights Team"),
    MEDIA_TEAM(value = 6, ministry = "Media Team"),
    SOUND_TEAM(value = 7, ministry = "Sound Team"),
    USHERS(value = 8, ministry = "Ushers"),
    NO_MINISTRY(value = 9, ministry = "No Ministry")
}