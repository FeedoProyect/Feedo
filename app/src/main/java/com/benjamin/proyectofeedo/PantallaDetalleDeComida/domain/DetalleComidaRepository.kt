package com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain

import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.RecetaDetalleModel

interface DetalleComidaRepository {
    suspend fun getRecetaDetalle(id: Int): RecetaDetalleModel?
}