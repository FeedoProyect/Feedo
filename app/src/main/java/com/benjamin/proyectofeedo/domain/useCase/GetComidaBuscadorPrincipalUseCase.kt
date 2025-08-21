package com.benjamin.proyectofeedo.domain.useCase

import com.benjamin.proyectofeedo.domain.Repository
import com.benjamin.proyectofeedo.domain.model.ComidasModel
import javax.inject.Inject

class GetComidaBuscadorPrincipalUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(name: String) = repository.getComidaBuscadorPrincipal(name) ?: emptyList()
}