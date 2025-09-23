package com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.useCase

import com.benjamin.proyectofeedo.PantallaDetalleDeComida.data.DetalleComidaRepositoryImpl
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.DetalleComidaRepository
import com.benjamin.proyectofeedo.PantallasPrincipales.data.repositoriosImpl.RepositoryImpl
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.RecetaDetalleModel
import javax.inject.Inject

class GetRecetaDetalleUseCase @Inject constructor(
    private val repository: DetalleComidaRepository
) {
    suspend operator fun invoke(id: Int): RecetaDetalleModel? = repository.getRecetaDetalle(id)
}