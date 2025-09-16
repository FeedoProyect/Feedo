package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoBuscador

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel

sealed class BuscadorPrincipalState {
    data object Loading : BuscadorPrincipalState()
    data class Error(val error: String) : BuscadorPrincipalState()
    data class Success(val comidasBuscador: List<ComidasModel>) : BuscadorPrincipalState()
    data object Empty : BuscadorPrincipalState()
}