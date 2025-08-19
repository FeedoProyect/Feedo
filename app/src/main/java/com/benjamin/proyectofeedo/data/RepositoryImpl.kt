package com.benjamin.proyectofeedo.data

import com.benjamin.proyectofeedo.data.Network.ComidasApiService
import com.benjamin.proyectofeedo.domain.Repository
import com.benjamin.proyectofeedo.domain.model.ComidasModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ComidasApiService) : Repository {

    override suspend fun getComidas(catalogoId: Int): List<ComidasModel>? {
        return try {
            val response = apiService.getRecetas("eq.$catalogoId")
            response.map { it.toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
    /*override suspend fun getPrediction(sign: String) : PredictionModel? {
        runCatching { apiService.getHoroscope(sign) }
            .onSuccess { return it.toDomain()}
            .onFailure { Log.i("Benja", "Ha ocurrido un error ${it.message}") }

        return null
    }*/
