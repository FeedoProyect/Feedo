package com.benjamin.proyectofeedo.pantallasPrincipales.data.repositoriosImpl

import android.content.Context
import android.net.Uri
import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService.ComidasFavoritosApiService
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.AddRecetaRequest
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios.AddComidaRepository
import com.google.gson.internal.bind.TypeAdapters.UUID
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import javax.inject.Inject

class AddComidaRepositoryImpl @Inject constructor(
    private val favoritosApiService: ComidasFavoritosApiService,
    private val context: Context,
    private val supabaseClient: SupabaseClient,

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