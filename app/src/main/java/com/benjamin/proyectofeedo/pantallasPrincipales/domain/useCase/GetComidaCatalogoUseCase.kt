package com.benjamin.proyectofeedo.pantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.Repository
import javax.inject.Inject

class GetComidaCatalogoUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(catalogoId: Int): Pair<List<ComidasModel>, List<ComidaDestacadaCatalogoModel>> {
        val comidas = repository.getComidas(catalogoId) ?: emptyList()
        val comidasDestacadas = repository.getComidasDestacadasCatalogo(catalogoId) ?: emptyList()
        return Pair(comidas, comidasDestacadas)
    }
}