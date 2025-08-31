package com.benjamin.proyectofeedo.pantallasPrincipales.domain.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FavoritosRequestModel(
    @SerializedName("id_usuario")
    val usuarioId: String,
    @SerializedName("receta_id")
    val recetaId: Int
)
