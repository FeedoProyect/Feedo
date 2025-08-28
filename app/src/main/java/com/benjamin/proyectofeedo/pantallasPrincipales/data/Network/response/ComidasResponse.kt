package com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
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