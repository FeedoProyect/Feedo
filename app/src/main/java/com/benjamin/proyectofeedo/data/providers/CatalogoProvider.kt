package com.benjamin.proyectofeedo.data.providers

import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.domain.model.CatalogoInfo
import com.benjamin.proyectofeedo.domain.model.CatalogoInfo.*
import javax.inject.Inject

class CatalogoProvider @Inject constructor() {
    fun getCatalogos(): List<CatalogoInfo>{
        return listOf(
             Desayunos,
             Almuerzos,
             Meriendas,
             Cena,
             Aperitivos,
             Elaboradas,
             Ensaladas,
             Postres,
             Rapidas,
             SinLactosa,
             SopasGuisos,
             Veganas,
             Vegetarianas
        )
    }
}