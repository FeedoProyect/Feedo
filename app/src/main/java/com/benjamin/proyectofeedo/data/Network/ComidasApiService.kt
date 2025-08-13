package com.benjamin.proyectofeedo.data.Network

import com.benjamin.proyectofeedo.data.Network.response.ComidasResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ComidasApiService {

    @GET("recetas")
    suspend fun getRecetas(
        @Query("select") select: String = "*" //por defecto trae todos los campos
    ): List<ComidasResponse>
}