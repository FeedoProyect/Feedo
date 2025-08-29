package com.benjamin.proyectofeedo.PantallaDetalleDeComida.data.network.response

import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.RecetaDetalleModel
import com.google.gson.annotations.SerializedName

data class RecetaDetalleResponse(
    val id: Int,
    val titulo: String,
    val imagen: String,
    val descripcion: String?,
    @SerializedName("tiempo_preparacion") val tiempoPreparacion: String?,
    val ingredientes: String?,
    val pasos: String?
) {
    fun toDomain() = RecetaDetalleModel(
        id = id,
        titulo = titulo,
        imagen = imagen,
        descripcion = descripcion,
        tiempoPreparacion = tiempoPreparacion,
        ingredientes = ingredientes
            ?.lines() // corta por saltos de línea
            ?.map { it.replace(Regex("^[0-9]+[).\\-]?"), "").trim() } // limpia numeración
            ?.filter { it.isNotBlank() }
            ?: emptyList(),
        pasos = pasos
            ?.lines()
            ?.map { it.replace(Regex("^[0-9]+[).\\-]?"), "").trim() }
            ?.filter { it.isNotBlank() }
            ?: emptyList()
    )
}