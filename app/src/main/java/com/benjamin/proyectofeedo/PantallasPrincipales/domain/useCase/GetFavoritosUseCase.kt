package com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosReceta
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.Repository
import javax.inject.Inject

class GetFavoritosUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(userId: String): List<FavoritosReceta> {
        return repository.getComidaFavoritos(userId) ?: emptyList()
    }
}
