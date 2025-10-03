package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment

import androidx.lifecycle.ViewModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FotoIngredientesModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.IngredienteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddIngredienteViewModel @Inject constructor(): ViewModel() {

    // Para manejar el ingrediente temporal del dialog
    private val _selectedIngredienteForDialog = MutableStateFlow<FotoIngredientesModel?>(null)
    val selectedIngredienteForDialog: StateFlow<FotoIngredientesModel?> = _selectedIngredienteForDialog

    // Lista de ingredientes agregados
    private val _ingredientesList = MutableStateFlow<List<IngredienteItem>>(emptyList())
    val ingredientesList: StateFlow<List<IngredienteItem>> = _ingredientesList

    // Para saber si estamos editando o agregando
    private val _isEditingMode = MutableStateFlow(false)
    val isEditingMode: StateFlow<Boolean> = _isEditingMode

    private val _ingredienteBeingEditedId = MutableStateFlow<String?>(null)
    val ingredienteBeingEditedId: StateFlow<String?> = _ingredienteBeingEditedId

    fun setSelectedIngrediente(ingrediente: FotoIngredientesModel) {
        _selectedIngredienteForDialog.value = ingrediente
    }

    fun clearSelectedIngrediente() {
        _selectedIngredienteForDialog.value = null
    }

    fun setEditingMode(isEditing: Boolean, ingrediente: IngredienteItem? = null) {
        _isEditingMode.value = isEditing
        _ingredienteBeingEditedId.value = ingrediente?.id
    }
    fun addIngrediente(nombre: String, imagen: String?) {
        val currentList = _ingredientesList.value.toMutableList()
        currentList.add(
            IngredienteItem(
                nombre = nombre,
                imagen = imagen
            )
        )
        _ingredientesList.value = currentList
    }


    fun updateIngrediente(id: String, nombre: String, imagen: String?) {
        val currentList = _ingredientesList.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1) {
            currentList[index] = currentList[index].copy(
                nombre = nombre,
                imagen = imagen
            )
            _ingredientesList.value = currentList
        }
    }


    fun deleteIngrediente(ingrediente: IngredienteItem) {
        val currentList = _ingredientesList.value.toMutableList()
        currentList.remove(ingrediente)
        _ingredientesList.value = currentList
    }

    fun getIngredientesList(): List<IngredienteItem> {
        return _ingredientesList.value
    }
}