package com.benjamin.proyectofeedo.pantallasPrincipales.data

import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.ComidasApiService
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.Repository
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
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

    override suspend fun getComidasDestacadasCatalogo(catalogoId: Int): List<ComidaDestacadaCatalogoModel>? {
        return try {
            val response = apiService.getComidaCatalogoDestacada("eq.$catalogoId")
            response.map { it.toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getComidaBuscadorPrincipal(name: String): List<ComidasModel>?{
        return try {
            val response = apiService.getComidaBuscador(nombre = "ilike.%${name}%")
            response.map { it.toDomain() }
        } catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }
}