package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoBuscador

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase.AddFavoritosUseCase
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase.GetComidaBuscadorPrincipalUseCase
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase.RemoveFavoritosUseCase
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
    private val addFavoritosUseCase: AddFavoritosUseCase,
    private val removeFavoritosUseCase: RemoveFavoritosUseCase // 👈 nuevo caso de uso
) : ViewModel() {

    private var _state = MutableStateFlow<BuscadorPrincipalState>(BuscadorPrincipalState.Loading)
    val state: StateFlow<BuscadorPrincipalState> = _state

    fun getComidasBuscador(comida: String) {
        viewModelScope.launch {
            _state.value = BuscadorPrincipalState.Loading
            try {
                val response = withContext(Dispatchers.IO) {
                    getComidaBuscadorPrincipalUseCase(comida)
                }
                if (response.isNullOrEmpty()) {
                    _state.value = BuscadorPrincipalState.Empty
                } else {
                    _state.value = BuscadorPrincipalState.Success(response)
                }
            } catch (e: Exception) {
                _state.value =
                    BuscadorPrincipalState.Error("Ha ocurrido un error, intentelo más tarde")
            }
        }
    }

    fun addComidasFavoritos(favoritos: FavoritosRequestModel) {
        viewModelScope.launch {
            val result = addFavoritosUseCase.add(favoritos)
            if (result.isSuccess) {
                Log.d("Favoritos", "Agregado OK")
            } else {
                Log.e("Favoritos", "Error: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun removeComidasFavoritos(favoritos: FavoritosRequestModel) {
        viewModelScope.launch {
            val result = removeFavoritosUseCase.remove(favoritos)
            if (result.isSuccess) {
                Log.d("Favoritos", "Eliminado OK")
            } else {
                Log.e("Favoritos", "Error al eliminar: ${result.exceptionOrNull()?.message}")
            }
        }
    }


    fun clearSearch() {
        _state.value = BuscadorPrincipalState.Empty
    }
}
