package com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.UserInformationModel

interface UserInformationRepository {
    suspend fun getUserInfo(uuid: String) : UserInformationModel?
}