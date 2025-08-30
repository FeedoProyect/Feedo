package com.benjamin.proyectofeedo.pantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios.FavoritosRepository
import javax.inject.Inject

class AddFavoritosUseCase @Inject constructor(
    private val favoritosRepository: FavoritosRepository
) {
    suspend operator fun invoke(favoritos: FavoritosRequestModel): Result<Unit>{
        return favoritosRepository.addFavorito(favoritos)
    }
}