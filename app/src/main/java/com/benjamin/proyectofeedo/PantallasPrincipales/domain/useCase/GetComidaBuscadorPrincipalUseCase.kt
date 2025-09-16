package com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.Repository
import javax.inject.Inject

class GetComidaBuscadorPrincipalUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(name: String) = repository.getComidaBuscadorPrincipal(name) ?: emptyList()
}