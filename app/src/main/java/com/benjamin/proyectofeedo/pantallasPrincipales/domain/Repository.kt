package com.benjamin.proyectofeedo.pantallasPrincipales.domain

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel

interface Repository {
    suspend fun getComidas(catalogoId: Int): List<ComidasModel>?

    suspend fun getComidasDestacadasCatalogo(catalogoId: Int): List<ComidaDestacadaCatalogoModel>?

    suspend fun getComidaBuscadorPrincipal(name: String): List<ComidasModel>?

}