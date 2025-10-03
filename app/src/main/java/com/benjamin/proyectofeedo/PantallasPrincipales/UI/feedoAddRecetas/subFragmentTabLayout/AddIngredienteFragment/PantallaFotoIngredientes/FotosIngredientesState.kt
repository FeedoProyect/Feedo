package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.PantallaFotoIngredientes

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FotoIngredientesModel

sealed class FotosIngredientesState {
    data object Loading : FotosIngredientesState()
    data class Error(private val message: String) : FotosIngredientesState()
    data class Success(val ingredientes: List<FotoIngredientesModel>) :
        FotosIngredientesState()
    data object Empty: FotosIngredientesState()
}