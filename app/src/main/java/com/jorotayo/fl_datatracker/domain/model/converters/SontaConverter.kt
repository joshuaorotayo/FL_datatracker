package com.jorotayo.fl_datatracker.domain.model.converters

import com.jorotayo.fl_datatracker.domain.model.Sonta
import com.jorotayo.fl_datatracker.domain.model.Sonta.AIRPORT_STARS
import com.jorotayo.fl_datatracker.domain.model.Sonta.DANCING_STARS
import com.jorotayo.fl_datatracker.domain.model.Sonta.FILM_STARS
import com.jorotayo.fl_datatracker.domain.model.Sonta.GREATER_LOVE_GOSPEL_CHOIR
import com.jorotayo.fl_datatracker.domain.model.Sonta.INSTRUMENTALISTS
import com.jorotayo.fl_datatracker.domain.model.Sonta.LIGHTS_TEAM
import com.jorotayo.fl_datatracker.domain.model.Sonta.MEDIA_TEAM
import com.jorotayo.fl_datatracker.domain.model.Sonta.NO_MINISTRY
import com.jorotayo.fl_datatracker.domain.model.Sonta.SOUND_TEAM
import com.jorotayo.fl_datatracker.domain.model.Sonta.USHERS
import io.objectbox.converter.PropertyConverter

class SontaConverter : PropertyConverter<Sonta, Int> {
    override fun convertToEntityProperty(databaseValue: Int): Sonta {
        return when (databaseValue) {
            0 -> AIRPORT_STARS
            1 -> DANCING_STARS
            2 -> FILM_STARS
            3 -> GREATER_LOVE_GOSPEL_CHOIR
            4 -> INSTRUMENTALISTS
            5 -> LIGHTS_TEAM
            6 -> MEDIA_TEAM
            7 -> SOUND_TEAM
            8 -> USHERS
            else -> NO_MINISTRY
        }
    }

    override fun convertToDatabaseValue(entityProperty: Sonta): Int {
        return entityProperty.value
    }
}