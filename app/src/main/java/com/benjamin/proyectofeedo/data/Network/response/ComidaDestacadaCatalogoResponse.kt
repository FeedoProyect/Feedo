package com.benjamin.proyectofeedo.data.Network.response

import com.benjamin.proyectofeedo.domain.model.ComidaDestacadaCatalogoModel
import com.google.gson.annotations.SerializedName

data class ComidaDestacadaCatalogoResponse (
    @SerializedName("titulo") val titulo: String,
    @SerializedName("imagen") val imagen: String,
    @SerializedName("tiempo_preparacion") val tiempo: String,
) {
    fun toDomain(): ComidaDestacadaCatalogoModel{
        return ComidaDestacadaCatalogoModel(
            titulo = titulo,
            imagen = imagen,
            tiempo = tiempo
        )
    }
}

