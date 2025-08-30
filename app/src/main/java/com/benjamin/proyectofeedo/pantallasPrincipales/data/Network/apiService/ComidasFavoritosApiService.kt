package com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService

import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response.AddFavoritosResponse
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ComidasFavoritosApiService {

    @Headers("Prefer: resolution=merge-duplicates,return=representation")
    @POST("favoritos")
    suspend fun addFavorito(@Body favorito: FavoritosRequestModel): List<AddFavoritosResponse>

}