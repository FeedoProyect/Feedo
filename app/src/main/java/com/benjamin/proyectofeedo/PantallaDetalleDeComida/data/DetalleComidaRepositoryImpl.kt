package com.benjamin.proyectofeedo.PantallaDetalleDeComida.data

import android.util.Log
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.data.NetworkDetalleComida.DetalleComidaApiService
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.DetalleComidaRepository
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.RecetaDetalleModel
import javax.inject.Inject

class DetalleComidaRepositoryImpl @Inject constructor(
    private val apiService: DetalleComidaApiService
): DetalleComidaRepository {
    //fari sacame este metodo para la carpeta detalle comida en data hijo de puta
    override suspend fun getRecetaDetalle(id: Int): RecetaDetalleModel? {
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