package com.benjamin.proyectofeedo.data.Network.response

import com.benjamin.proyectofeedo.domain.model.ComidasModel
import com.google.gson.annotations.SerializedName

data class ComidasResponse(
    @SerializedName("titulo") val titulo: String,
    @SerializedName("imagen") val imagen: String,
) {
    fun toDomain(): ComidasModel{
        return ComidasModel(
            titulo = titulo,
            imagen = imagen
        )
    }
}