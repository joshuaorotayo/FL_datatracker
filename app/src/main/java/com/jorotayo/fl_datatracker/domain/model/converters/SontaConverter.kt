package com.jorotayo.fl_datatracker.domain.model.converters

import com.jorotayo.fl_datatracker.domain.model.Sonta
import io.objectbox.converter.PropertyConverter

class SontaConverter : PropertyConverter<Sonta, Int> {
    override fun convertToEntityProperty(databaseValue: Int): Sonta {
        return when (databaseValue) {
            0 -> Sonta.AIRPORT_STARS
            1 -> Sonta.DANCING_STARS
            2 -> Sonta.FILM_STARS
            3 -> Sonta.GREATER_LOVE_GOSPEL_CHOIR
            4 -> Sonta.INSTRUMENTALISTS
            5 -> Sonta.LIGHTS_TEAM
            6 -> Sonta.MEDIA_TEAM
            7 -> Sonta.SOUND_TEAM
            8 -> Sonta.USHERS
            else -> Sonta.NO_MINISTRY
        }
    }

    override fun convertToDatabaseValue(entityProperty: Sonta): Int {
        return entityProperty.value
    }
}