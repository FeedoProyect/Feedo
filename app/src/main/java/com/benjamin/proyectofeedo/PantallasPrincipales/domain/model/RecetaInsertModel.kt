package com.benjamin.proyectofeedo.PantallasPrincipales.domain.model

data class RecetaInsertModel(
    val titulo: String,
    val descripcion: String? = null,
    val imagen: String? = null,
    val tiempoPreparacion: String? = null,
    val dificultad: String? = null,
    val pasos: String, // JSON string con los pasos
    val ingredientes: List<IngredienteInsertModel>
)

data class IngredienteInsertModel(
    val nombre: String,
    val imgIngrediente: String?
)