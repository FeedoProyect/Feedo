package com.benjamin.proyectofeedo.domain.useCase

import com.benjamin.proyectofeedo.domain.Repository
import javax.inject.Inject

class GetComidasUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(comida:String) = repository.getComidas(comida)

}