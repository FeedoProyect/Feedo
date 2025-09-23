package com.benjamin.proyectofeedo.PantallasPrincipales.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class IdFavoritosRecetaModel(
    val id_usuario: String,
    val receta_id: Int,
    val recetas: FavoritosReceta
)

@Serializable
data class FavoritosReceta(
    val id: Int,
    val titulo: String,
    val imagen: String
)