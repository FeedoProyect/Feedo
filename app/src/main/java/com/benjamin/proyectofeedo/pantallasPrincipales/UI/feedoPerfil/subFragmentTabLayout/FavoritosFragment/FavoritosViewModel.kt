package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoBuscador.BuscadorPrincipalState
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment.FavoritosState
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.useCase.GetComidasFavoritosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritosViewModel @Inject constructor(
    private val getComidasFavoritosUseCase: GetComidasFavoritosUseCase
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
}