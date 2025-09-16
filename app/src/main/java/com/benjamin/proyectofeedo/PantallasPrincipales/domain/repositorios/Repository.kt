package com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel

interface Repository {
    suspend fun getComidas(catalogoId: Int): List<ComidasModel>?

    suspend fun getComidasDestacadasCatalogo(catalogoId: Int): List<ComidaDestacadaCatalogoModel>?

    suspend fun getComidaCatalogoBuscador(name: String): List<ComidasModel>?

    suspend fun getComidaBuscadorPrincipal(name: String): List<ComidasModel>?

    suspend fun getComidaSeccionMenu(seccionId: Int): List<ComidasSeccionMenuModel>?

    suspend fun getComidaFavoritos(recetaId: String): List<ComidasModel>?
}