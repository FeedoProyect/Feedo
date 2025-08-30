package com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel

interface Repository {
    suspend fun getComidas(catalogoId: Int): List<ComidasModel>?

    suspend fun getComidasDestacadasCatalogo(catalogoId: Int): List<ComidaDestacadaCatalogoModel>?

    suspend fun getComidaBuscadorPrincipal(name: String): List<ComidasModel>?

    suspend fun getComidaSeccionMenu(seccionId: Int): List<ComidasSeccionMenuModel>?

    suspend fun getComidaFavoritos(recetaId: String): List<ComidasModel>?
}