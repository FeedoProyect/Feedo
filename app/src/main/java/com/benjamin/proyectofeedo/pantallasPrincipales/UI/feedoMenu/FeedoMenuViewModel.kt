package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.pantallasPrincipales.data.providers.CatalogoProvider
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.CatalogoInfo
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.useCase.GetComidaSeccionMenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedoMenuViewModel @Inject constructor(
    catalogoProvider: CatalogoProvider,
    private val getComidaSeccionMenuUseCase: GetComidaSeccionMenuUseCase
): ViewModel() {

    private var _catalogos = MutableStateFlow<List<CatalogoInfo>>(emptyList())
    val catalogos: StateFlow<List<CatalogoInfo>> = _catalogos

    private var _state = MutableStateFlow<Map<Int, FeedoMenuState>>(emptyMap())
    val state: StateFlow<Map<Int, FeedoMenuState>> = _state


    init {
        _catalogos.value = catalogoProvider.getCatalogos()

        listOf(1, 2, 3, 4, 5).forEach { seccionId ->
            getRecetasPorSeccion(seccionId)
        }
    }

    fun getRecetasPorSeccion(seccionId: Int){
        viewModelScope.launch {
            _state.value = _state.value.toMutableMap().apply {
                put(seccionId, FeedoMenuState.Loading)
            }

            try {
                val recetas = getComidaSeccionMenuUseCase(seccionId) ?: emptyList()
                _state.value = _state.value.toMutableMap().apply {
                    put(seccionId, FeedoMenuState.Success(recetas))
                }
            } catch (e: Exception) {
                _state.value = _state.value.toMutableMap().apply {
                    put(seccionId, FeedoMenuState.Error(e.message ?: "Error desconocido"))
                }
            }

        }
    }
}