package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel

sealed class FeedoMenuState {
    data object Loading : FeedoMenuState()
    data class Success(val recetas: List<ComidasSeccionMenuModel>) : FeedoMenuState()
    data class Error(val message: String) : FeedoMenuState()
}