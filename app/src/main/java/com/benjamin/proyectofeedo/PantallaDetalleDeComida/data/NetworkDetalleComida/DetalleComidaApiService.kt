package com.benjamin.proyectofeedo.PantallaDetalleDeComida.data.NetworkDetalleComida

import retrofit2.http.GET
import retrofit2.http.Query

interface DetalleComidaApiService {
    //fari sacame este metodo para la carpeta detalle comida en data hijo de puta
    @GET("recetas")
    suspend fun getRecetaDetalleById(
        @Query("select") select: String = "id,titulo,imagen,descripcion,tiempo_preparacion,ingredientes,pasos",
        @Query("id") id: String // debe ir "eq.<id>"
    ): List<RecetaDetalleResponse>
}