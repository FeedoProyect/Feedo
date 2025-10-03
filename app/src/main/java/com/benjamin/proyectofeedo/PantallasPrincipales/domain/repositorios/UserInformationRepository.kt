package com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.UserInformationModel

interface UserInformationRepository {
    suspend fun getUserInfo(idUsuario: String): UserInformationModel?
    suspend fun updateUserInfo(
        idUsuario: String,
        username: String,
        biografia: String,
        imagen_Perfil: String?
    ): Boolean
}
