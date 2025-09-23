package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosReceta

sealed class FavoritosState {
    data object Loading : FavoritosState()
    data class Success(val recetas: List<FavoritosReceta>?) : FavoritosState()
    data class Error(val message: String) : FavoritosState()
}