package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.detalleReceta

import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.RecetaDetalleModel



sealed class DetalleRecetaState {
    object Loading : DetalleRecetaState()
    data class Success(val receta: RecetaDetalleModel) : DetalleRecetaState()
    data class Error(val message: String) : DetalleRecetaState()
}
