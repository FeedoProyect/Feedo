package com.benjamin.proyectofeedo.PantallasPrincipales.data.repositoriosImpl

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosReceta
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.IdFavoritosRecetaModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.Repository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val client: SupabaseClient
) : Repository {

    override suspend fun getComidas(catalogoId: Int): List<ComidasModel>? {
        return try {
            val response = client.postgrest["recetas"]
                .select(
                    Columns.raw("id, titulo, imagen, receta_catalogo!inner(catalogo_id)")
                ) {
                    filter {
                        eq("receta_catalogo.catalogo_id", catalogoId)
                    }
                }
                .decodeList<ComidasModel>()

            response
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getComidasDestacadasCatalogo(catalogoId: Int): List<ComidaDestacadaCatalogoModel>? {
        return try {
            val response = client.postgrest["recetas"]
                .select(
                    Columns.raw("id, titulo, imagen, tiempo_preparacion, receta_catalogo2!inner(catalogo_id)")
                ){
                    filter{
                        eq("receta_catalogo2.catalogo_id", catalogoId)
                    }
                }
                .decodeList<ComidaDestacadaCatalogoModel>()

            response
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getComidaCatalogoBuscador(name: String): List<ComidasModel>? {
        return try {
            val response = client.postgrest["recetas"]
                .select(
                    Columns.raw("id, titulo, imagen, receta_catalogo!inner(catalogo_id)")
                ){
                    filter{
                        ilike("titulo", "$name%")
                    }
                }
                .decodeList<ComidasModel>()

            if (response.isEmpty()) null else response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getComidaBuscadorPrincipal(name: String): List<ComidasModel>? {
        return try {
            val response = client.postgrest["recetas"]
                .select(
                    Columns.raw("*")
                ){
                    filter{
                        ilike("titulo", "$name%")
                    }
                }
                .decodeList<ComidasModel>()
            if (response.isEmpty()) null else response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getComidaSeccionMenu(seccionId: Int): List<ComidasSeccionMenuModel> {
        return try {
            client.postgrest["receta_seccion"]
                .select(
                    Columns.raw("id, seccion_id, recetas(id,titulo,imagen)")
                ) {
                    filter { eq("seccion_id", seccionId) }
                }
                .decodeList<ComidasSeccionMenuModel>()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


    override suspend fun getComidaFavoritos(usuarioId: String): List<FavoritosReceta>? {
        return try {
            val response = client.postgrest["favoritos2"]
                .select(
                    Columns.raw("id_usuario, receta_id, recetas(id, titulo, imagen)")
                ){
                    filter{
                        eq("id_usuario", usuarioId)
                    }
                }
                .decodeList<IdFavoritosRecetaModel>()

            response.map { it.recetas }
        } catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }
}