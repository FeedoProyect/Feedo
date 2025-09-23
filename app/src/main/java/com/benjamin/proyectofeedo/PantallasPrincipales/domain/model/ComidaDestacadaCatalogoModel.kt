package com.benjamin.proyectofeedo.PantallasPrincipales.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ComidaDestacadaCatalogoModel(
    val id: Int,
    val titulo: String,
    val imagen: String,
    val tiempo_preparacion: String,
    val receta_catalogo2: List<RecetaCatalogoDestacada>?
)

@Serializable
data class RecetaCatalogoDestacada(
    val catalogo_id: Int
)