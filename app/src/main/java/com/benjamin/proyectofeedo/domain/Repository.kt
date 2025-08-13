package com.benjamin.proyectofeedo.domain


import com.benjamin.proyectofeedo.domain.model.ComidasModel

interface Repository {
    suspend fun getComidas(recetas: String): List<ComidasModel>?
}