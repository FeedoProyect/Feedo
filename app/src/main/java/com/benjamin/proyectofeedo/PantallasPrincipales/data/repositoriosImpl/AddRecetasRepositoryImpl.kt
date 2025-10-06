package com.benjamin.proyectofeedo.PantallasPrincipales.data.repositoriosImpl

import android.content.Context
import android.net.Uri
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.IngredienteInsertModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.RecetaInsertModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.AddRecetaRepository
import com.benjamin.proyectofeedo.utils.toByteArray
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.Serializable
import javax.inject.Inject

class AddRecetasRepositoryImpl @Inject constructor(
    private val client: SupabaseClient,
    @ApplicationContext private val context: Context
) : AddRecetaRepository {

    override suspend fun uploadImage(uri: Uri, fileName: String): Result<String> {
        return try {
            val bytes = uri.toByteArray(context)

            client.storage["recetas"].upload("$fileName.jpg", bytes)

            val imageUrl = client.storage["recetas"].publicUrl("$fileName.jpg")
            Result.success(imageUrl)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertReceta(receta: RecetaInsertModel): Result<Int> {
        return try {
            // 1. Insertar receta y obtener su ID
            val recetaId = insertRecetaBase(receta)

            // 2. Insertar ingredientes y obtener sus IDs
            val ingredientesIds = insertIngredientes(receta.ingredientes)

            // 3. Crear relaciones en receta_ingredientes
            insertRecetaIngredientes(recetaId, ingredientesIds)

            Result.success(recetaId)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    private suspend fun insertRecetaBase(receta: RecetaInsertModel): Int {
        val response = client.postgrest["recetas"]
            .insert(
                mapOf(
                    "titulo" to receta.titulo,
                    "descripcion" to receta.descripcion,
                    "imagen" to receta.imagen,
                    "tiempo_preparacion" to receta.tiempoPreparacion,
                    "pasos" to receta.pasos
                )
            ) {
                select(Columns.list("id"))
            }
            .decodeSingle<RecetaIdResponse>()

        return response.id
    }

    private suspend fun insertIngredientes(ingredientes: List<IngredienteInsertModel>): List<Int> {
        val ingredientesIds = mutableListOf<Int>()

        ingredientes.forEach { ingrediente ->
            // Primero verifica si el ingrediente ya existe
            val existente = client.postgrest["ingredientes"]
                .select(Columns.list("id")) {
                    filter {
                        eq("nombre", ingrediente.nombre)
                        eq("img_ingrediente", ingrediente.imgIngrediente ?: "")
                    }
                }
                .decodeList<IngredienteIdResponse>()
                .firstOrNull()

            if (existente != null) {
                ingredientesIds.add(existente.id)
            } else {
                // Si no existe, insertarlo
                val response = client.postgrest["ingredientes"]
                    .insert(
                        mapOf(
                            "nombre" to ingrediente.nombre,
                            "img_ingrediente" to ingrediente.imgIngrediente
                        )
                    ) {
                        select(Columns.list("id"))
                    }
                    .decodeSingle<IngredienteIdResponse>()

                ingredientesIds.add(response.id)
            }
        }

        return ingredientesIds
    }

    private suspend fun insertRecetaIngredientes(recetaId: Int, ingredientesIds: List<Int>) {
        val relaciones = ingredientesIds.map { ingredienteId ->
            mapOf(
                "receta_id" to recetaId,
                "ingrediente_id" to ingredienteId
            )
        }

        client.postgrest["receta_ingredientes"]
            .insert(relaciones)
    }
}

// Response models
@Serializable
data class RecetaIdResponse(val id: Int)

@Serializable
data class IngredienteIdResponse(val id: Int)