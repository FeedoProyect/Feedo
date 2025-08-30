package com.benjamin.proyectofeedo.pantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios.Repository
import javax.inject.Inject

class GetComidaSeccionMenuUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(seccionId: Int) = repository.getComidaSeccionMenu(seccionId)?: emptyList()
}