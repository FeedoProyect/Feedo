package com.benjamin.proyectofeedo.PantallasPrincipales.data.repositoriosImpl

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.AddComidaRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class AddComidaRepositoryImpl @Inject constructor(
    private val client: SupabaseClient
) : AddComidaRepository {

    override suspend fun addFavorito(favoritos: FavoritosRequestModel): Result<FavoritosRequestModel> {
        return try {
            val response = client.postgrest["favoritos2"]
                .insert(favoritos) {
                    select()
                }
                .decodeSingle<FavoritosRequestModel>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteFavorito(favoritosDelete: FavoritosRequestModel): Result<Unit> {
        return try {
            client.postgrest["favoritos2"].delete{
                filter {
                    eq("id_usuario", favoritosDelete.usuarioId)
                    eq("receta_id", favoritosDelete.recetaId)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}