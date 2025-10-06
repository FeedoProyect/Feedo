package com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios

import android.net.Uri
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.RecetaInsertModel

interface AddRecetaRepository {
    suspend fun insertReceta(receta: RecetaInsertModel): Result<Int>
    suspend fun uploadImage(uri: Uri, fileName: String): Result<String>
}