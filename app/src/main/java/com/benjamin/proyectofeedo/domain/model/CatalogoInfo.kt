package com.benjamin.proyectofeedo.domain.model

import com.benjamin.proyectofeedo.R


sealed class CatalogoInfo (val img: Int, val titulo: Int){
    data object Desayunos: CatalogoInfo(R.drawable.desayunos, R.string.Desayunos)
    data object Almuerzos: CatalogoInfo(R.drawable.almuerzos, R.string.Almuerzos)
    data object Meriendas: CatalogoInfo(R.drawable.meriendas, R.string.Meriendas)
    data object Cena: CatalogoInfo(R.drawable.cena, R.string.Cena)
    data object Aperitivos: CatalogoInfo(R.drawable.aperitivos, R.string.Aperitivos)
    data object Elaboradas: CatalogoInfo(R.drawable.elaboradas, R.string.Elaboradas)
    data object Ensaladas: CatalogoInfo(R.drawable.ensaladas, R.string.Ensaladas)
    data object Postres: CatalogoInfo(R.drawable.postres, R.string.Postres)
    data object Rapidas: CatalogoInfo(R.drawable.rapidas, R.string.Rapidas)
    data object Diabeticos: CatalogoInfo(R.drawable.sin_lactosa, R.string.diabeticos)
    data object SopasGuisos: CatalogoInfo(R.drawable.sopas_y_guisos, R.string.Sopas_y_guisos)
    data object Veganas: CatalogoInfo(R.drawable.veganas, R.string.Veganas)
    data object Vegetarianas: CatalogoInfo(R.drawable.vegetarianas, R.string.Vegetarianas)
}