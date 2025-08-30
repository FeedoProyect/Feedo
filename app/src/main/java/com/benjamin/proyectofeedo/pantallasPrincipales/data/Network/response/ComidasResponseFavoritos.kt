package com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.google.gson.annotations.SerializedName

data class ComidasResponseFavoritos(
    @SerializedName("recetas") val receta: RecetaResponseFavoritos?
) {
    fun toDomain(): ComidasModel {
        return ComidasModel(
            id = receta?.id ?: 0,
            titulo = receta?.titulo.orEmpty(),
            imagen = receta?.imagen.orEmpty()
        )
    }
}

data class RecetaResponseFavoritos(
    @SerializedName("id") val id: Int?,
    @SerializedName("titulo") val titulo: String?,
    @SerializedName("imagen") val imagen: String?
)
