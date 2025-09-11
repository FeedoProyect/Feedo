package com.benjamin.proyectofeedo.settingsFeedo.domain.useCase

import com.benjamin.proyectofeedo.settingsFeedo.domain.ThemeRepository
import com.benjamin.proyectofeedo.settingsFeedo.domain.model.DarkModeModel
import javax.inject.Inject

class SaveDarkModeUseCase @Inject constructor(private val repository: ThemeRepository) {
    suspend operator fun invoke(enabled: DarkModeModel) = repository.saveDarkMode(enabled)
}