package com.benjamin.proyectofeedo.PantallasPrincipales.data.repositoriosImpl

import com.benjamin.proyectofeedo.PantallasPrincipales.data.Network.apiService.UserInformationApiService
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.UserInformationModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.UserInformationRepository
import javax.inject.Inject

class UserInformationRepositoryImpl @Inject constructor(
    private val apiService: UserInformationApiService
) : UserInformationRepository {

    override suspend fun getUserInfo(uuid: String): UserInformationModel? {
        return try {
            val response = apiService.getUserInfo("eq.$uuid")
            response.firstOrNull()?.toDomain()
        } catch (e: Exception) {
            null
        }
    }
}