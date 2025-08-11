package com.benjamin.proyectofeedo.domain.model

import com.benjamin.proyectofeedo.R


sealed class CatalogoInfo (val img: Int, val titulo: Int){
    object Desayunos: CatalogoInfo(R.drawable.desayunos, R.string.Desayunos)
    object Almuerzos: CatalogoInfo(R.drawable.almuerzos, R.string.Almuerzos)
    object Meriendas: CatalogoInfo(R.drawable.meriendas, R.string.Meriendas)
    object Cena: CatalogoInfo(R.drawable.cena, R.string.Cena)
    object Aperitivos: CatalogoInfo(R.drawable.aperitivos, R.string.Aperitivos)
    object Elaboradas: CatalogoInfo(R.drawable.elaboradas, R.string.Elaboradas)
    object Ensaladas: CatalogoInfo(R.drawable.ensaladas, R.string.Ensaladas)
    object Postres: CatalogoInfo(R.drawable.postres, R.string.Postres)
    object Rapidas: CatalogoInfo(R.drawable.rapidas, R.string.Rapidas)
    object SinLactosa: CatalogoInfo(R.drawable.sin_lactosa, R.string.Sin_lactosa)
    object SopasGuisos: CatalogoInfo(R.drawable.sopas_y_guisos, R.string.Sopas_y_guisos)
    object Veganas: CatalogoInfo(R.drawable.veganas, R.string.Veganas)
    object Vegetarianas: CatalogoInfo(R.drawable.vegetarianas, R.string.Vegetarianas)
}