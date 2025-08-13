package com.benjamin.proyectofeedo.UI.ListaCatalogos

sealed class CatalogosListState {
    data object Loading: CatalogosListState()
    data class Error(val error: String): CatalogosListState()
    data class Succes(val data: String): CatalogosListState()
}