package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoContenidoCatalogos

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel

sealed class CatalogosListComidasState {
    data object Loading : CatalogosListComidasState()
    data class Error(val error: String) : CatalogosListComidasState()
    data class Success(
        val comidas: List<ComidasModel>?,
        val comidasDestacadas: List<ComidaDestacadaCatalogoModel>
    ) : CatalogosListComidasState()
    object Empty : CatalogosListComidasState()
}