package com.benjamin.proyectofeedo.PantallasPrincipales.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoritosRequestModel(
    @SerialName("id_usuario")
    val usuarioId: String,
    @SerialName("receta_id")
    val recetaId: Int
)
