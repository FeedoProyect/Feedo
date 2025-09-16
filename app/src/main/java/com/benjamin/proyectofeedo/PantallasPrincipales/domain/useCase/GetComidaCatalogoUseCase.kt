package com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.Repository
import javax.inject.Inject

class GetComidaCatalogoUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(catalogoId: Int): Pair<List<ComidasModel>, List<ComidaDestacadaCatalogoModel>> {
        val comidas = repository.getComidas(catalogoId) ?: emptyList()
        val comidasDestacadas = repository.getComidasDestacadasCatalogo(catalogoId) ?: emptyList()
        return Pair(comidas, comidasDestacadas)
    }
}