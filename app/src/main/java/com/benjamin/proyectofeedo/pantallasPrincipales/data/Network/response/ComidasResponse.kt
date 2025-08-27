package com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.google.gson.annotations.SerializedName

data class ComidasResponse(
    @SerializedName("titulo") val titulo: String,
    @SerializedName("imagen") val imagen: String,
) {
    fun toDomain(): ComidasModel {
        return ComidasModel(
            titulo = titulo,
            imagen = imagen,
        )
    }
}