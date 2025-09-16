package com.benjamin.proyectofeedo.PantallasPrincipales.domain.model

import com.google.gson.annotations.SerializedName

data class AddRecetaRequest(
    @SerializedName("id_usuario")
    val usuarioId: String,
    @SerializedName("id_receta")
    val recetaId: String
)