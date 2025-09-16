package com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.UserInformationRepository
import javax.inject.Inject

class GetUserInformationUseCase @Inject constructor(private val repository: UserInformationRepository) {
    suspend operator fun invoke(uuid: String) = repository.getUserInfo(uuid)
}