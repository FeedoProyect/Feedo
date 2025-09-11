package com.benjamin.proyectofeedo.settingsFeedo.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.benjamin.proyectofeedo.core.datastore.Local.DataStorePreferences
import com.benjamin.proyectofeedo.settingsFeedo.domain.ThemeRepository
import com.benjamin.proyectofeedo.settingsFeedo.domain.model.DarkModeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val dataStore: DataStorePreferences
) : ThemeRepository {

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    override fun getDarkMode(): Flow<DarkModeModel> =
        dataStore.getBoolean(DARK_MODE_KEY).map { DarkModeModel(it) }

    override suspend fun saveDarkMode(enabled: DarkModeModel) {
        dataStore.saveBoolean(DARK_MODE_KEY, enabled.darkMode)
    }
}