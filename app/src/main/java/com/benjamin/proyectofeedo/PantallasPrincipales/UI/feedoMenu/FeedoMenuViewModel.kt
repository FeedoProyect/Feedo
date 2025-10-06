package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.PantallasPrincipales.data.providers.CatalogoProvider
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.CatalogoInfo
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosReceta
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase.AddFavoritosUseCase
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase.GetComidaSeccionMenuUseCase
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase.GetFavoritosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedoMenuViewModel @Inject constructor(
    catalogoProvider: CatalogoProvider,
    private val getComidaSeccionMenuUseCase: GetComidaSeccionMenuUseCase,
    private val addFavoritosUseCase: AddFavoritosUseCase,
    private val getFavoritosUseCase: GetFavoritosUseCase
) : ViewModel() {

    private var _catalogos = MutableStateFlow<List<CatalogoInfo>>(emptyList())
    val catalogos: StateFlow<List<CatalogoInfo>> = _catalogos

    private var _state = MutableStateFlow<Map<Int, FeedoMenuState>>(emptyMap())
    val state: StateFlow<Map<Int, FeedoMenuState>> = _state

    private val _favoritos = MutableStateFlow<List<FavoritosReceta>>(emptyList())
    val favoritos: StateFlow<List<FavoritosReceta>> = _favoritos


    init {
        _catalogos.value = catalogoProvider.getCatalogos()

        // Obtener las secciones del menú
        listOf(1, 2, 3, 4, 5).forEach { seccionId ->
            getRecetasPorSeccion(seccionId)
        }

        // 🔹 Obtener los favoritos al iniciar
        getFavoritos("1") // <-- acá pasá el id real del usuario
    }

    fun getRecetasPorSeccion(seccionId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.toMutableMap().apply {
                put(seccionId, FeedoMenuState.Loading)
            }

            try {
                val recetas = getComidaSeccionMenuUseCase(seccionId)
                _state.value = _state.value.toMutableMap().apply {
                    put(seccionId, FeedoMenuState.Success(recetas))
                }
            } catch (e: Exception) {
                _state.value = _state.value.toMutableMap().apply {
                    put(seccionId, FeedoMenuState.Error(e.message ?: "No tienes conexión a Internet"))
                }
            }
        }
    }

    // ✅ Nuevo: obtener favoritos del usuario
    fun getFavoritos(userId: String) {
        viewModelScope.launch {
            try {
                val result = getFavoritosUseCase(userId)
                _favoritos.value = result
                Log.d("Favoritos", "✅ Favoritos obtenidos: ${result.size}")
            } catch (e: Exception) {
                Log.e("Favoritos", "❌ Error al obtener favoritos: ${e.message}")
            }
        }
    }

    fun addComidasFavoritos(favoritos: FavoritosRequestModel) {
        viewModelScope.launch {
            val result = addFavoritosUseCase.add(favoritos)

            if (result.isSuccess) {
                Log.d("Favoritos", "Agregado OK")
                getFavoritos(favoritos.usuarioId) // refrescar lista
            } else {
                Log.e("Favoritos", "Error: ${result.exceptionOrNull()?.message}")
            }
        }
    }
}

