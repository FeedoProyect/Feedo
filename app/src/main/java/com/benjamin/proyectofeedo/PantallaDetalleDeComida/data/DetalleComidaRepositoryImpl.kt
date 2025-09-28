package com.benjamin.proyectofeedo.PantallaDetalleDeComida.data.repositoriosImpl

import RecetaDetalleResponse
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.data.NetworkDetalleComida.toDomain
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.DetalleComidaRepository
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.RecetaDetalleModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class DetalleComidaRepositoryImpl @Inject constructor(
    private val client: SupabaseClient
) : DetalleComidaRepository {

    override suspend fun getRecetaDetalle(id: Int): RecetaDetalleModel? {
        return try {
            val response = client.postgrest["recetas"]
                .select(
                    Columns.raw(
                        """
                        id,
                        titulo,
                        imagen,
                        descripcion,
                        tiempo_preparacion,
                        pasos,
                        receta_ingredientes (
                            ingredientes (
                                id,
                                nombre,
                                img_ingrediente
                            )
                        )
                        """.trimIndent()
                    )
                ) {
                    filter { eq("id", id) }
                    single() //
                }
                .decodeAs<RecetaDetalleResponse>()

            response.toDomain()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}