package com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel

interface FavoritosRepository {

    suspend fun addFavorito(favoritos: FavoritosRequestModel): Result<Unit>
}