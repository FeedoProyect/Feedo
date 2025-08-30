package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel

sealed class FavoritosState {
    data object Loading : FavoritosState()
    data class Success(val recetas: List<ComidasModel>?) : FavoritosState()
    data class Error(val message: String) : FavoritosState()
}