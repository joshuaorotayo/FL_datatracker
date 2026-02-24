package com.jorotayo.fl_datatracker.domain.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferenceStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    // ── Read as Flow (reactive) ───────────────────────────────────────────
    fun getStringFlow(key: SettingsKeys): Flow<String> =
        dataStore.data.map { it[stringPreferencesKey(key.name)] ?: "" }

    fun getBooleanFlow(key: SettingsKeys): Flow<Boolean> =
        dataStore.data.map { it[booleanPreferencesKey(key.name)] == true }

    // ── Read once (suspend) ───────────────────────────────────────────────
    suspend fun getString(key: SettingsKeys): String =
        getStringFlow(key).first()

    suspend fun getBoolean(key: SettingsKeys): Boolean =
        getBooleanFlow(key).first()

    // ── Write (suspend) ───────────────────────────────────────────────────
    suspend fun setString(vararg values: Pair<SettingsKeys, String>) {
        dataStore.edit { prefs ->
            values.forEach { (key, value) ->
                prefs[stringPreferencesKey(key.name)] = value
            }
        }
    }

    suspend fun setBoolean(vararg values: Pair<SettingsKeys, Boolean>) {
        dataStore.edit { prefs ->
            values.forEach { (key, value) ->
                prefs[booleanPreferencesKey(key.name)] = value
            }
        }
    }

    suspend fun clearValue(key: SettingsKeys) {
        dataStore.edit { prefs ->
            prefs.remove(booleanPreferencesKey(key.name))
            prefs.remove(stringPreferencesKey(key.name))
        }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}