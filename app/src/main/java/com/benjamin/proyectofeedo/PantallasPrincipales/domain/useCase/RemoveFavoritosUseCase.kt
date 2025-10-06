package com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.AddFavComidaRepository
import javax.inject.Inject

class RemoveFavoritosUseCase @Inject constructor(
    private val repository: AddFavComidaRepository
) {
    suspend fun remove(favoritos: FavoritosRequestModel): Result<Unit> {
        return repository.deleteFavorito(favoritos)
    }
}
