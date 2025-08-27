package com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.google.gson.annotations.SerializedName

data class ComidasMenuSeccionResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("imagen") val imagen: String,
    val receta_seccion: List<RecetaSeccion>
)  {
    fun toDomain(): ComidasSeccionMenuModel {
        return ComidasSeccionMenuModel(
            id = id,
            titulo = titulo,
            imagen = imagen,
        )
    }
}

data class RecetaSeccion(
    val seccion_id: Int
)
