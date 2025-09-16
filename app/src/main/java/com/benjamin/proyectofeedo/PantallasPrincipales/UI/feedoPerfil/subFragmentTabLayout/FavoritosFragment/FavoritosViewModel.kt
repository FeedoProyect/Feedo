package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase.AddFavoritosUseCase
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase.GetComidasFavoritosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritosViewModel @Inject constructor(
    private val getComidasFavoritosUseCase: GetComidasFavoritosUseCase,
    private val addFavoritosUseCase: AddFavoritosUseCase
): ViewModel() {

    private var _state = MutableStateFlow<FavoritosState>(FavoritosState.Loading)
    val state: StateFlow<FavoritosState> = _state

    fun getComidaFavoritos(usuarioId: String) {
        viewModelScope.launch {
            _state.value = FavoritosState.Loading
            try {
                val response = withContext(Dispatchers.IO) {
                    getComidasFavoritosUseCase(usuarioId)
                }
                _state.value = FavoritosState.Success(response)
            } catch (e: Exception) {
                _state.value = FavoritosState.Error("Ha ocurrido un error, intentelo más tarde")
            }
        }
    }

    fun deleteComidaFavorito(usuarioId: String, recetaId: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    addFavoritosUseCase(usuarioId, recetaId) // 👈 acá usás el otro use case
                }
                // refrescar lista después de borrar
                getComidaFavoritos(usuarioId)
            } catch (e: Exception) {
                _state.value = FavoritosState.Error("Error al eliminar favorito")
            }
        }
    }
}