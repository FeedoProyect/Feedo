package com.benjamin.proyectofeedo.PantallaDetalleDeComida.data.NetworkDetalleComida

import RecetaDetalleResponse
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.IngredienteModel
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.RecetaDetalleModel

fun RecetaDetalleResponse.toDomain(): RecetaDetalleModel {
    return RecetaDetalleModel(
        id = id,
        titulo = titulo,
        imagen = imagen,
        descripcion = descripcion,
        tiempoPreparacion = tiempo_preparacion,
        pasos = pasos
            ?.split("\n")        // divide el texto por saltos de línea
            ?.map { it.trim() }  // limpia espacios
            ?: emptyList(),      // si es null, lista vacía
        ingredientes = receta_ingredientes.map { ingredienteResponse ->
            IngredienteModel(
                id = ingredienteResponse.ingredientes.id,
                nombre = ingredienteResponse.ingredientes.nombre,
                imagen = ingredienteResponse.ingredientes.img_ingrediente
            )
        }
    )
}


