package com.benjamin.proyectofeedo.PantallasPrincipales.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ComidasSeccionMenuModel(
    val id: Int,
    val seccion_id: Int,
    val recetas: RecetaSeccion
)

@Serializable
data class RecetaSeccion(
    val id: Int,
    val titulo: String,
    val imagen: String,
    var esFavorito: Boolean = false   // 👈 nuevo campo
)
