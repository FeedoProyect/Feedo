package com.benjamin.proyectofeedo.PantallasPrincipales.data.repositoriosImpl

import android.util.Log
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
            Log.d("FavoritosRepo", "🟢 Intentando agregar favorito: usuario=${favoritos.usuarioId}, receta=${favoritos.recetaId}")

            // 1️⃣ Verificar si ya existe ese favorito antes de insertar
            val existing = client.postgrest["favoritos2"]
                .select {
                    filter {
                        eq("id_usuario", favoritos.usuarioId)
                        eq("receta_id", favoritos.recetaId)
                    }
                }
                .decodeList<FavoritosRequestModel>()

            if (existing.isNotEmpty()) {
                Log.d("FavoritosRepo", "⚠️ Ya existe el favorito en la base. No se inserta de nuevo.")
                return Result.success(existing.first())
            }

            // 2️⃣ Intentar insertar el nuevo favorito
            val response = client.postgrest["favoritos2"]
                .insert(favoritos) {
                    select()
                }
                .decodeSingleOrNull<FavoritosRequestModel>() // <- más seguro

            if (response != null) {
                Log.d("FavoritosRepo", "✅ Favorito insertado correctamente: $response")
                Result.success(response)
            } else {
                Log.e("FavoritosRepo", "⚠️ Supabase no devolvió respuesta (posible error o duplicado silencioso)")
                Result.failure(Exception("Insert vacío o duplicado"))
            }

        } catch (e: Exception) {
            Log.e("FavoritosRepo", "❌ Error al insertar favorito: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun deleteFavorito(favoritosDelete: FavoritosRequestModel): Result<Unit> {
        return try {
            Log.d("FavoritosRepo", "🗑️ Eliminando favorito: usuario=${favoritosDelete.usuarioId}, receta=${favoritosDelete.recetaId}")
            client.postgrest["favoritos2"].delete {
                filter {
                    eq("id_usuario", favoritosDelete.usuarioId)
                    eq("receta_id", favoritosDelete.recetaId)
                }
            }
            Log.d("FavoritosRepo", "✅ Favorito eliminado correctamente")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FavoritosRepo", "❌ Error al eliminar favorito: ${e.message}", e)
            Result.failure(e)
        }
    }
}
