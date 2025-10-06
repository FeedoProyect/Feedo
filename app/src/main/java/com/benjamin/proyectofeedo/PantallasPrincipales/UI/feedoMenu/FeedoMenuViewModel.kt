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
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedoMenuViewModel @Inject constructor(
    catalogoProvider: CatalogoProvider,
    private val getComidaSeccionMenuUseCase: GetComidaSeccionMenuUseCase,
    private val addFavoritosUseCase: AddFavoritosUseCase,
    private val getFavoritosUseCase: GetFavoritosUseCase,
    private val supabaseClient: SupabaseClient
) : ViewModel() {

    private var _catalogos = MutableStateFlow<List<CatalogoInfo>>(emptyList())
    val catalogos: StateFlow<List<CatalogoInfo>> = _catalogos

    private var _state = MutableStateFlow<Map<Int, FeedoMenuState>>(emptyMap())
    val state: StateFlow<Map<Int, FeedoMenuState>> = _state

    private val _favoritos = MutableStateFlow<List<FavoritosReceta>>(emptyList())
    val favoritos: StateFlow<List<FavoritosReceta>> = _favoritos

    init {
        _catalogos.value = catalogoProvider.getCatalogos()
        listOf(1, 2, 3, 4, 5).forEach { getRecetasPorSeccion(it) }
        getFavoritos("1")
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
                Log.d("Favoritos", "✅ Agregado correctamente")
                getFavoritos(favoritos.usuarioId)
            } else {
                Log.e("Favoritos", "❌ Error al agregar: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun deleteComidasFavoritos(favorito: FavoritosRequestModel) {
        viewModelScope.launch {
            try {
                supabaseClient
                    .from("favoritos2")
                    .delete {
                        filter {
                            eq("id_usuario", favorito.usuarioId)
                            eq("receta_id", favorito.recetaId)
                        }
                    }

                Log.d("FeedoVM", "🗑️ Favorito eliminado correctamente")
                getFavoritos(favorito.usuarioId)
            } catch (e: Exception) {
                Log.e("FeedoVM", "❌ Error al eliminar favorito (FeedoMenuViewModel)", e)
            }
        }
    }
    fun getComidaFavoritos(userId: String) {
        viewModelScope.launch {
            try {
                // ✅ Llamamos directamente al use case (usa operator fun invoke)
                val favoritos = getFavoritosUseCase(userId)

                Log.d("Favoritos", "✅ Favoritos recargados (${favoritos.size}) desde MenuViewModel")

                // Si necesitás actualizar LiveData o StateFlow, hacelo acá
                // _state.value = _state.value.copy(favoritos = favoritos)

            } catch (e: Exception) {
                Log.e("Favoritos", "❌ Error al recargar favoritos: ${e.message}")
            }
        }
    }


}



