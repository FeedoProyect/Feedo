package com.benjamin.proyectofeedo.settingsFeedo.domain.useCase

import com.benjamin.proyectofeedo.settingsFeedo.domain.PreferenceRepository
import javax.inject.Inject

class GetDarkModeUseCase@Inject constructor(private val repository: PreferenceRepository) {
     operator fun invoke() = repository.getDarkMode()
}