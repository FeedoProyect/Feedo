package com.benjamin.proyectofeedo.data.Network

import com.benjamin.proyectofeedo.data.Network.response.ComidasResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ComidasApiService {

    @GET("recetas?select=*, receta_catalogo!inner(catalogo_id)")
    suspend fun getRecetas(
        @Query("receta_catalogo.catalogo_id") catalogoId: String
    ): List<ComidasResponse>
}