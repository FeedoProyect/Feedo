package com.benjamin.proyectofeedo.UI.ListaCatalogos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.domain.useCase.GetComidasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatalogosListComidasViewModel @Inject constructor(private val getComidasUseCase: GetComidasUseCase): ViewModel() {

    private var _state = MutableStateFlow<CatalogosListComidasState>(CatalogosListComidasState.Loading)
    val state: StateFlow<CatalogosListComidasState> = _state

    fun getComidass(comida: String){
        viewModelScope.launch {
            _state.value = CatalogosListComidasState.Loading
            val result = withContext(Dispatchers.IO){ getComidasUseCase(comida) } //hilo secundario

            if(result != null){
                _state.value = CatalogosListComidasState.Success(result)
            }else{
                _state.value = CatalogosListComidasState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }
}