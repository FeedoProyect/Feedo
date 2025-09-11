package com.benjamin.proyectofeedo.settingsFeedo.domain

import com.benjamin.proyectofeedo.settingsFeedo.domain.model.DarkModeModel
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getDarkMode(): Flow<DarkModeModel>
    suspend fun saveDarkMode(enabled: DarkModeModel)
}