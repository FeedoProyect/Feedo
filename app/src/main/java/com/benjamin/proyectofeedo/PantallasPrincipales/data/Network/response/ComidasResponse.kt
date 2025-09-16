package com.benjamin.proyectofeedo.PantallasPrincipales.data.Network.response

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.google.gson.annotations.SerializedName

data class ComidasResponse(
    @SerializedName("titulo") val titulo: String,
    @SerializedName("imagen") val imagen: String,
    @SerializedName("id") val id: Int,
) {
    fun toDomain(): ComidasModel {
        return ComidasModel(
            id= id,
            titulo = titulo,
            imagen = imagen,
        )
    }
}