package com.benjamin.proyectofeedo.domain.useCase

import com.benjamin.proyectofeedo.domain.Repository
import com.benjamin.proyectofeedo.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.domain.model.ComidasModel
import javax.inject.Inject

class GetComidasUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(catalogoId: Int): Pair<List<ComidasModel>, List<ComidaDestacadaCatalogoModel>> {
        val comidas = repository.getComidas(catalogoId) ?: emptyList()
        val comidasDestacadas = repository.getComidasDestacadasCatalogo(catalogoId) ?: emptyList()
        return Pair(comidas, comidasDestacadas)
    }
}
