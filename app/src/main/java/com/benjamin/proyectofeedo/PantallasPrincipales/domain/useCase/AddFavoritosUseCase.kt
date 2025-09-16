package com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.AddComidaRepository
import javax.inject.Inject

class AddFavoritosUseCase @Inject constructor(
    private val favoritosRepository: AddComidaRepository
) {
    suspend operator fun invoke(favoritos: FavoritosRequestModel): Result<Unit>{
        return favoritosRepository.addFavorito(favoritos)
    }

    suspend operator fun invoke(usuarioId: String, recetaId: Int): Result<Unit>{
        return favoritosRepository.deleteFavorito(usuarioId, recetaId)
    }
}