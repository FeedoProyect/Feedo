package com.benjamin.proyectofeedo.PantallasPrincipales.domain.usecases

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.UserInformationRepository
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val repository: UserInformationRepository
) {
    suspend operator fun invoke(
        idUsuario: String,
        username: String,
        biografia: String,
        imagen_Perfil: String?
    ): Boolean {
        return repository.updateUserInfo(idUsuario, username, biografia, imagen_Perfil)
    }
}
