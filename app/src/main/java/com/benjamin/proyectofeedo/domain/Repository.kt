package com.benjamin.proyectofeedo.domain


import com.benjamin.proyectofeedo.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.domain.model.ComidasModel

interface Repository {
    suspend fun getComidas(catalogoId: Int): List<ComidasModel>?

    suspend fun getComidasDestacadasCatalogo(catalogoId: Int): List<ComidaDestacadaCatalogoModel>?

    suspend fun getComidaBuscadorPrincipal(name: String): List<ComidasModel>?

}