package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoBuscador

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoContenidoCatalogos.CatalogosListComidasState
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.useCase.AddFavoritosUseCase
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.useCase.GetComidaBuscadorPrincipalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BuscadorPrincipalViewModel @Inject constructor(
    private val getComidaBuscadorPrincipalUseCase: GetComidaBuscadorPrincipalUseCase,
    private val addFavoritosUseCase: AddFavoritosUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<BuscadorPrincipalState>(BuscadorPrincipalState.Loading)
    val state: StateFlow<BuscadorPrincipalState> = _state

    fun getComidasBuscador(comida: String){
        viewModelScope.launch {
            _state.value = BuscadorPrincipalState.Loading
            try {
                val response = withContext(Dispatchers.IO) {
                    getComidaBuscadorPrincipalUseCase(comida)
                }
                if (response == null || response.isEmpty()) {
                    _state.value = BuscadorPrincipalState.Empty // 👈 “no se encontró nada”
                } else {
                    _state.value = BuscadorPrincipalState.Success(response)
                }
            } catch (e: Exception) {
                _state.value = BuscadorPrincipalState.Error("Ha ocurrido un error, intentelo más tarde")
            }
        }
    }

    fun addComidasFavoritos(favoritos: FavoritosRequestModel) {
        viewModelScope.launch {
            val result = addFavoritosUseCase(favoritos)

            if (result.isSuccess) {
                Log.d("Favoritos", "Agregado OK")
            } else {
                Log.e("Favoritos", "Error: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun clearSearch(){
        _state.value = BuscadorPrincipalState.Empty
    }
}