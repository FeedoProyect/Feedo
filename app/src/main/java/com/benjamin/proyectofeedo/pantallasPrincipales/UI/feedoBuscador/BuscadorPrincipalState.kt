package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoBuscador

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel

sealed class BuscadorPrincipalState {
    data object Loading : BuscadorPrincipalState()
    data class Error(val error: String) : BuscadorPrincipalState()
    data class Success(val comidasBuscador: List<ComidasModel>) : BuscadorPrincipalState()
}