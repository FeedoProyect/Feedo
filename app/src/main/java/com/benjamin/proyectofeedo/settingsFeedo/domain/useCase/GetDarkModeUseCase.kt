package com.benjamin.proyectofeedo.settingsFeedo.domain.useCase

import com.benjamin.proyectofeedo.settingsFeedo.domain.ThemeRepository
import javax.inject.Inject

class GetDarkModeUseCase@Inject constructor(private val repository: ThemeRepository) {
     operator fun invoke() = repository.getDarkMode()
}