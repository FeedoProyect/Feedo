package com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.UserInformationModel

interface UserInformationRepository {
    suspend fun getUserInfo(uuid: String) : UserInformationModel?
}