package com.benjamin.proyectofeedo.PantallasPrincipales.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ComidasModel(
    val id: Int,
    val titulo: String,
    val imagen: String,
    val receta_catalogo: List<RecetaCatalogo>? = null
)

@Serializable
data class RecetaCatalogo(
    val catalogo_id: Int
)