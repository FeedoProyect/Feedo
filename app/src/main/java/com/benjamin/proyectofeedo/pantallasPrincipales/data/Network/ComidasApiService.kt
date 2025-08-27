package com.benjamin.proyectofeedo.pantallasPrincipales.data.Network

import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response.ComidaDestacadaCatalogoResponse
import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response.ComidasMenuSeccionResponse
import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response.ComidasResponse
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


}
