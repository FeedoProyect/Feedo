package com.benjamin.proyectofeedo.PantallasPrincipales.data.providers

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.CatalogoInfo
import javax.inject.Inject

class CatalogoProvider @Inject constructor() {
    fun getCatalogos(): List<CatalogoInfo>{
        return listOf(
            CatalogoInfo.Desayunos,
            CatalogoInfo.Almuerzos,
            CatalogoInfo.Meriendas,
            CatalogoInfo.Cena,
            CatalogoInfo.Aperitivos,
            CatalogoInfo.Elaboradas,
            CatalogoInfo.Ensaladas,
            CatalogoInfo.Postres,
            CatalogoInfo.Rapidas,
            CatalogoInfo.Diabeticos,
            CatalogoInfo.SopasGuisos,
            CatalogoInfo.Veganas,
            CatalogoInfo.Vegetarianas
        )
    }
}