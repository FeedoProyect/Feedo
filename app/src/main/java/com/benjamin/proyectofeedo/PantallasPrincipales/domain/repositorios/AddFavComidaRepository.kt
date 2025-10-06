package com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel

interface AddFavComidaRepository {

    suspend fun addFavorito(favoritos: FavoritosRequestModel): Result<FavoritosRequestModel>
    suspend fun deleteFavorito(favoritosDelete: FavoritosRequestModel): Result<Unit>
}