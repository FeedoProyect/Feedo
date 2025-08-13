package com.benjamin.proyectofeedo.UI.ListaCatalogos

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CatalogosListViewModel @Inject constructor(): ViewModel() {

    private var _state = MutableStateFlow<CatalogosListState>(CatalogosListState.Loading)
    val state: StateFlow<CatalogosListState> = _state
}