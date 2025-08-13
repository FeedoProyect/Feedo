package com.benjamin.proyectofeedo.UI.ListaCatalogos

import com.benjamin.proyectofeedo.domain.model.ComidasModel

sealed class CatalogosListComidasState {
    data object Loading: CatalogosListComidasState()
    data class Error(val error: String): CatalogosListComidasState()
    data class Success(val comidas: List<ComidasModel>): CatalogosListComidasState()
}