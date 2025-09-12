package com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService

import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response.UserInformationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserInformationApiService {

    @GET("usuarios2")
    suspend fun getUserInfo(
        @Query("id_usuario", encoded = true) uuid: String,
        @Query("select") select: String = "*"
    ): List<UserInformationResponse>
}
