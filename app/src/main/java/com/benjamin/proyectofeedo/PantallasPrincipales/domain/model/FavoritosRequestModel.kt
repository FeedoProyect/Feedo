package com.benjamin.proyectofeedo.PantallasPrincipales.domain.model

import com.google.gson.annotations.SerializedName

data class FavoritosRequestModel(
    @SerializedName("id_usuario")
    val usuarioId: String,
    @SerializedName("receta_id")
    val recetaId: Int
)
