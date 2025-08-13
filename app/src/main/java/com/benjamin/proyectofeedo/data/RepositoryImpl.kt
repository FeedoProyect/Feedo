package com.benjamin.proyectofeedo.data

import com.benjamin.proyectofeedo.data.Network.ComidasApiService
import com.benjamin.proyectofeedo.data.Network.response.ComidasResponse
import com.benjamin.proyectofeedo.domain.Repository
import com.benjamin.proyectofeedo.domain.model.ComidasModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ComidasApiService) : Repository {

    override suspend fun getComidas(receta: String): List<ComidasModel>? {
        return try {
            val response = apiService.getRecetas()
            response.map { it.toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}