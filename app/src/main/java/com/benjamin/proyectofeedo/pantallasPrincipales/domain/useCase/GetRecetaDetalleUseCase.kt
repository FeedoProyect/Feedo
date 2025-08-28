
package com.benjamin.proyectofeedo.pantallasPrincipales.domain.useCase

import android.util.Log
import com.benjamin.proyectofeedo.pantallasPrincipales.data.RepositoryImpl
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.RecetaDetalleModel
import javax.inject.Inject

class GetRecetaDetalleUseCase @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) {
    suspend operator fun invoke(id: Int): RecetaDetalleModel? = repositoryImpl.getRecetaDetalle(id)
}

