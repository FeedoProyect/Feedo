package com.benjamin.proyectofeedo.pantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.Repository
import javax.inject.Inject

class GetComidaBuscadorPrincipalUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(name: String) = repository.getComidaBuscadorPrincipal(name) ?: emptyList()
}