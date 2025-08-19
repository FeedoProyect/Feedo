package com.benjamin.proyectofeedo.UI.feedoMenu

import androidx.lifecycle.ViewModel
import com.benjamin.proyectofeedo.data.providers.CatalogoProvider
import com.benjamin.proyectofeedo.domain.model.CatalogoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FeedoMenuViewModel @Inject constructor(catalogoProvider: CatalogoProvider): ViewModel() {

    private var _catalogos = MutableStateFlow<List<CatalogoInfo>>(emptyList())
    val catalogos: StateFlow<List<CatalogoInfo>> = _catalogos

    init {
        _catalogos.value = catalogoProvider.getCatalogos()
    }
}