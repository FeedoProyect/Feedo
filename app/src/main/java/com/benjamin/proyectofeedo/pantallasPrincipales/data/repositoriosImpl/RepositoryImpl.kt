package com.benjamin.proyectofeedo.pantallasPrincipales.data.repositoriosImpl

import android.util.Log
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.RecetaDetalleModel
import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService.ComidasApiService
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios.Repository
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
            val response = apiService.getComidaBuscador(nombre = "ilike.${name}%")
            response.map { it.toDomain() }
        } catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getComidaSeccionMenu(seccionId: Int): List<ComidasSeccionMenuModel>? {
        return try {
            val response = apiService.getComidaSeccion(seccionId = "eq.$seccionId")
            response.map { it.toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getComidaFavoritos(recetaId: String): List<ComidasModel>? {
        return try {
            val response = apiService.getComidaFavoritos(
                usuarioId = "eq.$recetaId"
            )
            response.map { it.toDomain() }
        } catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }



    //fari sacame este metodo para la carpeta detalle comida en data hijo de puta
    suspend fun getRecetaDetalle(id: Int): RecetaDetalleModel? {
        return try {
            val list = apiService.getRecetaDetalleById(id = "eq.$id")

            val response = list.firstOrNull()
            response?.toDomain()

        } catch (e: Exception) {
            Log.e("RepositoryImpl", "Error al obtener detalle receta", e)
            null
        }
    }
}