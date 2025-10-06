package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.Paso
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.IngredienteInsertModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.IngredienteItem
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.RecetaInsertModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.AddRecetaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.http.ContentType.Application
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class AddRecetaViewModel @Inject constructor(
    private val recetaRepository: AddRecetaRepository
) : ViewModel() {

    // Estados existentes
    private val _pasos = MutableStateFlow<List<Paso>>(emptyList())
    val pasos: StateFlow<List<Paso>> = _pasos

    // Nuevos estados para la receta completa
    private val _titulo = MutableStateFlow("")
    val titulo: StateFlow<String> = _titulo

    private val _tiempoPreparacion = MutableStateFlow("")
    val tiempoPreparacion: StateFlow<String> = _tiempoPreparacion

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    private val _ingredientes = MutableStateFlow<List<IngredienteItem>>(emptyList())
    val ingredientes: StateFlow<List<IngredienteItem>> = _ingredientes

    // Estado de la inserción
    private val _insertState = MutableStateFlow<InsertState>(InsertState.Idle)
    val insertState: StateFlow<InsertState> = _insertState

    fun resetInsertState() {
        _insertState.value = InsertState.Idle
    }

    // Métodos para actualizar datos
    fun setTitulo(titulo: String) {
        _titulo.value = titulo
    }

    fun setTiempoPreparacion(tiempo: String) {
        _tiempoPreparacion.value = tiempo
    }

    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri
    }

    fun setIngredientes(ingredientes: List<IngredienteItem>) {
        _ingredientes.value = ingredientes
    }

    // ============= MÉTODOS DE PASOS (COMPLETOS) =============
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

    // MÉTODO PRINCIPAL PARA GUARDAR TODO
    fun guardarReceta() {
        viewModelScope.launch {
            _insertState.value = InsertState.Loading

            try {
                // 1. Validaciones
                if (_titulo.value.isBlank()) {
                    _insertState.value = InsertState.Error("Debes agregar un título")
                    return@launch
                }

                if (_pasos.value.isEmpty()) {
                    _insertState.value = InsertState.Error("Debes agregar al menos un paso")
                    return@launch
                }

                // 2. Subir imagen si existe
                var imageUrl: String? = null
                _imageUri.value?.let { uri ->
                    val fileName = "receta_${System.currentTimeMillis()}"
                    val result = recetaRepository.uploadImage(uri, fileName)

                    if (result.isSuccess) {
                        imageUrl = result.getOrNull()
                    } else {
                        _insertState.value = InsertState.Error("Error al subir imagen")
                        return@launch
                    }
                }

                // 3. Convertir pasos a JSON
                val pasosJson = Json.encodeToString(_pasos.value.map { it.instruccion })

                // 4. Preparar ingredientes
                val ingredientesInsert = _ingredientes.value.map {
                    IngredienteInsertModel(
                        nombre = it.nombre,
                        imgIngrediente = it.imagen
                    )
                }

                // 5. Crear modelo de receta
                val receta = RecetaInsertModel(
                    titulo = _titulo.value,
                    descripcion = null,
                    imagen = imageUrl,
                    tiempoPreparacion = _tiempoPreparacion.value.takeIf { it.isNotBlank() },
                    dificultad = null,
                    pasos = pasosJson,
                    ingredientes = ingredientesInsert
                )

                // 6. Insertar en Supabase
                val result = recetaRepository.insertReceta(receta)

                if (result.isSuccess) {
                    _insertState.value = InsertState.Success(result.getOrNull() ?: 0)
                    limpiarFormulario()
                } else {
                    _insertState.value = InsertState.Error("Error al guardar la receta")
                }

            } catch (e: Exception) {
                _insertState.value = InsertState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    private fun limpiarFormulario() {
        _titulo.value = ""
        _tiempoPreparacion.value = ""
        _imageUri.value = null
        _ingredientes.value = emptyList()
        _pasos.value = emptyList()
    }
}

sealed class InsertState {
    object Idle : InsertState()
    object Loading : InsertState()
    data class Success(val recetaId: Int) : InsertState()
    data class Error(val message: String) : InsertState()
}