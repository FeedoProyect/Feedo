package com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel

interface AddComidaRepository {

    suspend fun addFavorito(favoritos: FavoritosRequestModel): Result<Unit>
}