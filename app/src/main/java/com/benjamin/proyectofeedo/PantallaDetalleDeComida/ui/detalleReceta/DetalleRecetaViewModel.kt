package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.detalleReceta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.useCase.GetRecetaDetalleUseCase
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase.AddFavoritosUseCase
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase.GetComidasFavoritosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleRecetaViewModel @Inject constructor(
    private val getRecetaDetalleUseCase: GetRecetaDetalleUseCase,
    private val addFavoritosUseCase: AddFavoritosUseCase,
    private val getFavoritosUseCase: GetComidasFavoritosUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DetalleRecetaState>(DetalleRecetaState.Loading)
    val state: StateFlow<DetalleRecetaState> = _state.asStateFlow()

    private val _isFavorite = MutableStateFlow<Boolean?>(null)
    val isFavorite: StateFlow<Boolean?> = _isFavorite.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    private var userId: String? = null

    fun setUserId(uid: String) {
        userId = uid
    }

    fun load(id: Int) {
        viewModelScope.launch {
            _state.value = DetalleRecetaState.Loading
            val receta = getRecetaDetalleUseCase(id)
            if (receta != null) {
                _state.value = DetalleRecetaState.Success(receta)
                checkIfFavorite(id)
            } else {
                _state.value = DetalleRecetaState.Error("No se pudo cargar la receta")
            }
        }
    }

    fun checkIfFavorite(recetaId: Int) {
        viewModelScope.launch {
            val uid = userId ?: return@launch
            try {
                val favoritos = getFavoritosUseCase(uid)
                val esFav = favoritos?.any { it.id == recetaId } == true
                _isFavorite.value = esFav
            } catch (e: Exception) {
                _isFavorite.value = null
            }
        }
    }

    fun toggleFavorite(recetaId: Int) {
        viewModelScope.launch {
            val uid = userId ?: return@launch
            if (_isProcessing.value) return@launch

            _isProcessing.value = true
            val request = FavoritosRequestModel(usuarioId = uid, recetaId = recetaId)

            try {
                val current = _isFavorite.value ?: false
                if (current) {
                    addFavoritosUseCase.delete(request)
                    _isFavorite.value = false
                } else {
                    addFavoritosUseCase.add(request)
                    _isFavorite.value = true
                }
            } catch (e: Exception) {
                checkIfFavorite(recetaId)
            } finally {
                _isProcessing.value = false
            }
        }
    }
}







