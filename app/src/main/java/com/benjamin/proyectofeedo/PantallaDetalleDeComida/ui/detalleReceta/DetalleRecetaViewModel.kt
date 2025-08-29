package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.detalleReceta

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.RecetaDetalleModel
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.useCase.GetRecetaDetalleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleRecetaViewModel @Inject constructor(
    private val getRecetaDetalle: GetRecetaDetalleUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RecetaDetalleModel?>(null)
    val state: StateFlow<RecetaDetalleModel?> = _state

    fun load(id: Int) {
        viewModelScope.launch {
            _state.value = getRecetaDetalle(id)
        }
        fun load(id: Int) {
            viewModelScope.launch {
                val detalle = getRecetaDetalle(id)
                Log.d("DetalleRecetaViewModel", "Receta recibida del UseCase: $detalle")
                _state.value = detalle
            }
        }

    }
}