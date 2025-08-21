package com.benjamin.proyectofeedo.UI.feedoBuscador

import com.benjamin.proyectofeedo.domain.model.ComidasModel

sealed class BuscadorPrincipalState {
    data object Loading : BuscadorPrincipalState()
    data class Error(val error: String) : BuscadorPrincipalState()
    data class Success(val comidasBuscador: List<ComidasModel>) : BuscadorPrincipalState()
}