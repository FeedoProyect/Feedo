package com.benjamin.proyectofeedo.UI.feedoContenidoCatalogos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.domain.useCase.GetComidaCatalogoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatalogosListComidasViewModel @Inject constructor(private val getComidasUseCase: GetComidaCatalogoUseCase): ViewModel() {

    private var _state = MutableStateFlow<CatalogosListComidasState>(CatalogosListComidasState.Loading)
    val state: StateFlow<CatalogosListComidasState> = _state

    fun getComidass(comida: Int){
        viewModelScope.launch {
            _state.value = CatalogosListComidasState.Loading
            try {
                val (comidas, comidasDestacadas) = withContext(Dispatchers.IO) {
                    getComidasUseCase(comida)
                }
                _state.value = CatalogosListComidasState.Success(comidas, comidasDestacadas)
            } catch (e: Exception) {
                _state.value = CatalogosListComidasState.Error("Ha ocurrido un error, intentelo más tarde")
            }
        }
    }
}