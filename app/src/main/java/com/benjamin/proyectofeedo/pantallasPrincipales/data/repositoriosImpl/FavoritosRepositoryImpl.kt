package com.benjamin.proyectofeedo.pantallasPrincipales.data.repositoriosImpl

import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService.ComidasFavoritosApiService
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios.FavoritosRepository
import javax.inject.Inject

class FavoritosRepositoryImpl @Inject constructor(
    private val favoritosApiService: ComidasFavoritosApiService
) : FavoritosRepository {

    override suspend fun addFavorito(favoritos: FavoritosRequestModel): Result<Unit> {
        return try {
            val response = favoritosApiService.addFavorito(favoritos)

            if (response.isNotEmpty()) {
                Result.success(Unit) // ✅ éxito
            } else {
                Result.failure(Exception("No se insertó el favorito"))
            }
        }catch (e: Exception) {
            Result.failure(e) // 🚨 error (timeout, 400, etc.)
        }
    }
}