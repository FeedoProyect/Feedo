package com.benjamin.proyectofeedo.core.datastore.Local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.benjamin.proyectofeedo.settingsFeedo.domain.model.DarkModeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun getBoolean(key: Preferences.Key<Boolean>, defaultValue: Boolean = false): Flow<Boolean> {
        return dataStore.data.map { prefs -> prefs[key] ?: defaultValue }
    }

    suspend fun saveBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { prefs -> prefs[key] = value }
    }

    fun getString(key: Preferences.Key<String>, defaultValue: String? = null): Flow<String?> {
        return dataStore.data.map { prefs -> prefs[key] ?: defaultValue }
    }

    suspend fun saveString(key: Preferences.Key<String>, value: String) {
        dataStore.edit { prefs -> prefs[key] = value }
    }

    fun getInt(key: Preferences.Key<Int>, defaultValue: Int = -1): Flow<Int> {
        return dataStore.data.map { prefs -> prefs[key] ?: defaultValue }
    }

    suspend fun saveInt(key: Preferences.Key<Int>, value: Int) {
        dataStore.edit { prefs -> prefs[key] = value }
    }

    suspend fun clear(key: Preferences.Key<*>) {
        dataStore.edit { prefs -> prefs.remove(key) }
    }
}
