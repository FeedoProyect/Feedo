package com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel

interface AddComidaRepository {

    suspend fun addFavorito(favoritos: FavoritosRequestModel): Result<Unit>
    suspend fun deleteFavorito(usuarioId: String, recetaId: Int): Result<Unit>
}