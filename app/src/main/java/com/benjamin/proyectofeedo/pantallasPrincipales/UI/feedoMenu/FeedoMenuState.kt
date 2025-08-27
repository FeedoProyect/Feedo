package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel

sealed class FeedoMenuState {
    data object Loading : FeedoMenuState()
    data class Success(val recetas: List<ComidasSeccionMenuModel>) : FeedoMenuState()
    data class Error(val message: String) : FeedoMenuState()
}