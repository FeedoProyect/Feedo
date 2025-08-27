package com.benjamin.proyectofeedo.settingsFeedo.data.Local

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
    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    val darkModeFlow: Flow<DarkModeModel> = dataStore.data.map { preferences ->
        DarkModeModel(preferences[DARK_MODE_KEY] ?: false)
    }

    suspend fun saveDarkmode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }
}


