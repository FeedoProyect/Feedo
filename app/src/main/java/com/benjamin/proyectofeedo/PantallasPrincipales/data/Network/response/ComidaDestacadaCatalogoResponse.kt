package com.benjamin.proyectofeedo.PantallasPrincipales.data.Network.response

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.google.gson.annotations.SerializedName

data class ComidaDestacadaCatalogoResponse (
    @SerializedName("id") val id : Int,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("imagen") val imagen: String,
    @SerializedName("tiempo_preparacion") val tiempo: String,
) {
    fun toDomain(): ComidaDestacadaCatalogoModel {
        return ComidaDestacadaCatalogoModel(
            id = id,
            titulo = titulo,
            imagen = imagen,
            tiempo = tiempo
        )
    }
}