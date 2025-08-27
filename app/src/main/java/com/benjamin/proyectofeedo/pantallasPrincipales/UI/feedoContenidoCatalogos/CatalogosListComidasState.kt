package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoContenidoCatalogos

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel

sealed class CatalogosListComidasState {
    data object Loading : CatalogosListComidasState()
    data class Error(val error: String) : CatalogosListComidasState()
    data class Success(
        val comidas: List<ComidasModel>,
        val comidasDestacadas: List<ComidaDestacadaCatalogoModel>
    ) : CatalogosListComidasState()
}