package com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService

import com.benjamin.proyectofeedo.PantallaDetalleDeComida.data.response.RecetaDetalleResponse
import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response.ComidaDestacadaCatalogoResponse
import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response.ComidasMenuSeccionResponse
import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response.ComidasResponse
import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response.ComidasResponseFavoritos
import retrofit2.http.GET
import retrofit2.http.Query

interface ComidasApiService {

    @GET("recetas?select=*, receta_catalogo!inner(catalogo_id)")
    suspend fun getRecetas(
        @Query("receta_catalogo.catalogo_id") catalogoId: String
    ): List<ComidasResponse>

    @GET("recetas?select=*, receta_catalogo2!inner(catalogo_id)")
    suspend fun getComidaCatalogoDestacada(
        @Query("receta_catalogo2.catalogo_id") comidaCatalogoDestacada: String
    ): List<ComidaDestacadaCatalogoResponse>

    @GET("recetas?select=*")
    suspend fun buscarRecetasCatalogo(
        @Query("titulo") titulo: String
    ): List<ComidasResponse>

    @GET("recetas")
    suspend fun getComidaBuscador(
        @Query("select") select: String = "*",
        @Query("titulo") nombre: String? = null
    ): List<ComidasResponse>

    @GET("recetas")
    suspend fun getComidaSeccion(
        @Query("select") select: String = "*,receta_seccion!inner(seccion_id)",
        @Query("receta_seccion.seccion_id") seccionId: String
    ): List<ComidasMenuSeccionResponse>

    @GET("favoritos2")
    suspend fun getComidaFavoritos(
        @Query("select") select: String = "recetas(*)",
        @Query("id_usuario") usuarioId: String
    ): List<ComidasResponseFavoritos>




    //fari sacame este metodo para la carpeta detalle comida en data hijo de puta
    @GET("recetas")
    suspend fun getRecetaDetalleById(
        @Query("select") select: String = "id,titulo,imagen,descripcion,tiempo_preparacion,ingredientes,pasos",
        @Query("id") id: String // debe ir "eq.<id>"
    ): List<RecetaDetalleResponse>


}