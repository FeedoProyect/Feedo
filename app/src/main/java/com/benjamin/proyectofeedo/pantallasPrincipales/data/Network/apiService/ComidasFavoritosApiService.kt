package com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService

import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response.AddFavoritosResponse
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ComidasFavoritosApiService {

    @Headers("Prefer: resolution=merge-duplicates,return=representation")
    @POST("favoritos2")
    suspend fun addFavorito(@Body favorito: FavoritosRequestModel): List<AddFavoritosResponse>


    @DELETE("favoritos2")
    suspend fun deleteFavorito(
        @Query("id_usuario", encoded = true) usuarioId: String,
        @Query("receta_id", encoded = true) recetaId: String
    ): Response<Unit>


}