package com.benjamin.proyectofeedo.data.providers

import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.domain.model.CatalogoInfo
import com.benjamin.proyectofeedo.domain.model.CatalogoInfo.*
import javax.inject.Inject


/* Este provider de catalogos es para el primer listado de catalgos del menu principal
*  su uso es para poder acceder desde el fragment al viewmodel y saber que lista de datos devuelve*/

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
             Diabeticos,
             SopasGuisos,
             Veganas,
             Vegetarianas
        )
    }
}