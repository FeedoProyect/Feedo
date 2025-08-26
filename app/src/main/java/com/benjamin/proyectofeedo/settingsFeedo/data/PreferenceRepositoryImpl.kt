package com.benjamin.proyectofeedo.settingsFeedo.data

import com.benjamin.proyectofeedo.settingsFeedo.data.Local.DataStorePreferences
import com.benjamin.proyectofeedo.settingsFeedo.domain.PreferenceRepository
import com.benjamin.proyectofeedo.settingsFeedo.domain.model.DarkModeModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(
    private val dataStore: DataStorePreferences
) : PreferenceRepository {

    override fun getDarkMode(): Flow<DarkModeModel> = dataStore.darkModeFlow

    override suspend fun saveDarkMode(enabled: DarkModeModel) {
        dataStore.saveDarkmode(enabled.darkMode)
    }
}