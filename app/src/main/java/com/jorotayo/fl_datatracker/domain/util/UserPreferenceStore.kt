package com.jorotayo.fl_datatracker.domain.util

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class UserPreferenceStore @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun getString(key: SharedPreferenceKey) = sharedPreferences.getString(key.name, "")

    fun getBoolean(key: SharedPreferenceKey) = sharedPreferences.getBoolean((key.name), false)

    fun setString(vararg values: Pair<SharedPreferenceKey, String>) = sharedPreferences.edit {
        values.forEach { (key, value) ->
            putString(key.name, value)
        }
    }

    fun setBoolean(vararg values: Pair<SharedPreferenceKey, Boolean>) = sharedPreferences.edit {
        values.forEach { (key, value) ->
            putBoolean(key.name, value)
        }
    }

    fun clearValue(key: SharedPreferenceKey) = sharedPreferences.edit {
        remove(key.name)
    }

    fun clear() = sharedPreferences.edit {
        clear()
    }
}