package com.benjamin.proyectofeedo.data.response

import kotlinx.serialization.Serializable

@Serializable
data class CatalogoResponse (
    val id: Int,
    val titulo: String,
    val imagen: String
)