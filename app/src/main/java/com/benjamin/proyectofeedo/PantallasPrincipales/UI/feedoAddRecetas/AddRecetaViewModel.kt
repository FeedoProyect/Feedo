package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas

import androidx.lifecycle.ViewModel
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.Paso
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddRecetaViewModel @Inject constructor() : ViewModel() {

    private val _pasos = MutableStateFlow<List<Paso>>(emptyList())
    val pasos: StateFlow<List<Paso>> = _pasos

    fun setCantidadPasos(cantidad: Int) {
        val pasosActuales = _pasos.value.toMutableList()
        val cantidadActual = pasosActuales.size

        when {
            cantidad > cantidadActual -> {
                for (i in cantidadActual until cantidad) {
                    val pasoNuevo = Paso("Paso ${i + 1}", "")
                    pasosActuales.add(pasoNuevo)
                }
            }

            cantidad < cantidadActual -> {
                while (pasosActuales.size > cantidad) {
                    pasosActuales.removeAt(pasosActuales.size - 1)
                }
            }
        }
        _pasos.value = pasosActuales
    }

    fun addInstruccion(instruccion: String) {
        val listaActual = _pasos.value.toMutableList()
        val nuevoPaso = Paso("Paso ${listaActual.size + 1}", instruccion)
        listaActual.add(nuevoPaso)
        _pasos.value = listaActual
    }

    // En tu AddRecetaViewModel
    fun editarInstruccion(paso: Paso, nuevaInstruccion: String) {
        val pasosActuales = _pasos.value.toMutableList()
        val index = pasosActuales.indexOfFirst { it.numero == paso.numero }

        if (index != -1) {
            pasosActuales[index] = paso.copy(instruccion = nuevaInstruccion)
            _pasos.value = pasosActuales
        }
    }

    fun eliminarPaso(paso: Paso) {
        val pasosActuales = _pasos.value.toMutableList()
        pasosActuales.remove(paso)

        val pasosRenumerados = pasosActuales.mapIndexed { index, pasoExistente ->
            pasoExistente.copy(numero = "Paso ${index + 1}")
        }
        _pasos.value = pasosRenumerados
    }
}