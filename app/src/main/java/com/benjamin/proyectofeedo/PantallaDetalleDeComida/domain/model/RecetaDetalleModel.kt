package com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model

import java.io.Serializable

data class RecetaDetalleModel(
    val id: Int,
    val titulo: String,
    val imagen: String?,
    val descripcion: String?,
    val tiempoPreparacion: String?,
    val pasos: List<String>,
    val ingredientes: List<IngredienteModel>
)

data class IngredienteModel(
    val id: Int,
    val nombre: String,
    val imagen: String?
) : Serializable