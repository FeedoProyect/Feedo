
package com.benjamin.proyectofeedo.pantallasPrincipales.domain.model

data class RecetaDetalleModel(
    val id: Int,
    val titulo: String,
    val imagen: String,
    val descripcion: String?,
    val tiempoPreparacion: String?,
    val ingredientes: List<String>,
    val pasos: List<String>
)
