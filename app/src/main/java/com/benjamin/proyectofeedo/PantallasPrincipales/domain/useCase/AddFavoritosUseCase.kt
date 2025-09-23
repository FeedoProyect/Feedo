package com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.AddComidaRepository
import javax.inject.Inject

class AddFavoritosUseCase @Inject constructor(
    private val favoritosRepository: AddComidaRepository
) {
    suspend fun add(favoritos: FavoritosRequestModel): Result<FavoritosRequestModel>{
        return favoritosRepository.addFavorito(favoritos)
    }

    suspend fun delete(favoritosDelete: FavoritosRequestModel): Result<Unit>{
        return favoritosRepository.deleteFavorito(favoritosDelete)
    }
}