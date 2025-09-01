package com.benjamin.proyectofeedo.pantallasPrincipales.data.repositoriosImpl

import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService.ComidasFavoritosApiService
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios.AddComidaRepository
import javax.inject.Inject

class AddComidaRepositoryImpl @Inject constructor(
    private val favoritosApiService: ComidasFavoritosApiService,

) : AddComidaRepository {

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

    override suspend fun deleteFavorito(usuarioId: String, recetaId: Int): Result<Unit> {
        return try {
            val response = favoritosApiService.deleteFavorito("eq.$usuarioId", "eq.$recetaId")
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val error = response.errorBody()?.string()
                Result.failure(Exception("Error al eliminar favorito: ${response.code()} - $error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}