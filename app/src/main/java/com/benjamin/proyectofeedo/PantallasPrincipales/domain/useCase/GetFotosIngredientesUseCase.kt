package com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.Repository
import javax.inject.Inject

class GetFotosIngredientesUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(name: String) = repository.getFotosIngredientes(name)

}