package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.detalleReceta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.useCase.GetRecetaDetalleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetalleRecetaViewModel @Inject constructor(
    private val getRecetaDetalleUseCase: GetRecetaDetalleUseCase
) : ViewModel() {

    private var _state =
        MutableStateFlow<DetalleRecetaState>(DetalleRecetaState.Loading)
    val state: StateFlow<DetalleRecetaState> = _state

    fun load(id: Int) {
        viewModelScope.launch {
            _state.value = DetalleRecetaState.Loading
            val receta = getRecetaDetalleUseCase(id)
            if (receta != null) {
                _state.value = DetalleRecetaState.Success(receta)
            } else {
                _state.value = DetalleRecetaState.Error("No se pudo cargar la receta")
            }
        }
    }

}
