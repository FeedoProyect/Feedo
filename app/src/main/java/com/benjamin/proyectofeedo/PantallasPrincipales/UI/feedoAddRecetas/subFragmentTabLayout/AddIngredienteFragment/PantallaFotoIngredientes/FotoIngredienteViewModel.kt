package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.PantallaFotoIngredientes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.PantallaFotoIngredientes.FotosIngredientesState
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoBuscador.BuscadorPrincipalState
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase.GetFotosIngredientesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FotoIngredienteViewModel @Inject constructor(
    private val getFotosIngredientesUseCase: GetFotosIngredientesUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<FotosIngredientesState>(FotosIngredientesState.Loading)
    val state: StateFlow<FotosIngredientesState> = _state

    init {
        getFotosIngredientes("")
    }

    fun getFotosIngredientes(name: String){
        viewModelScope.launch {
            _state.value = FotosIngredientesState.Loading
            try {
                val response = withContext(Dispatchers.IO) {
                    getFotosIngredientesUseCase(name)
                }
                if(response == null || response.isEmpty()){
                    _state.value = FotosIngredientesState.Empty
                } else {
                    _state.value = FotosIngredientesState.Success(response)
                }
            } catch (e: Exception) {
                _state.value = FotosIngredientesState.Error("Ha ocurrido un error, intentelo más tarde")
            }
        }
    }
}