package com.benjamin.proyectofeedo.pantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios.Repository
import javax.inject.Inject

class GetComidaBuscadorCatalogoUseCase @Inject constructor( private val repository: Repository) {

    suspend operator fun invoke(name: String) = repository.getComidaCatalogoBuscador(name)
}