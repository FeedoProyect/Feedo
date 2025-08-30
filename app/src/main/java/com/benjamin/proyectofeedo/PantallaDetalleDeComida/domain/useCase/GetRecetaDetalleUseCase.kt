package com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.useCase

import com.benjamin.proyectofeedo.pantallasPrincipales.data.repositoriosImpl.RepositoryImpl
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.RecetaDetalleModel
import javax.inject.Inject

class GetRecetaDetalleUseCase @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) {
    suspend operator fun invoke(id: Int): RecetaDetalleModel? = repositoryImpl.getRecetaDetalle(id)
}