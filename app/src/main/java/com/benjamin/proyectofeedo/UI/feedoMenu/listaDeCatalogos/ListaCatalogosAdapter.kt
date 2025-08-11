package com.benjamin.proyectofeedo.UI.feedoMenu.listaDeCatalogos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.data.response.CatalogoResponse
import com.benjamin.proyectofeedo.databinding.ItemListaCatalogoBinding
import com.benjamin.proyectofeedo.domain.model.CatalogoInfo

class ListaCatalogosAdapter(
    private var catalogosList: List<CatalogoInfo> = emptyList(),
    private val onItemSelected: (CatalogoInfo) -> Unit
) :
    RecyclerView.Adapter<ListaCatalogosViewHolder>() {

    fun updateList(list: List<CatalogoInfo>) {
        catalogosList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaCatalogosViewHolder {
        val binding =
            ItemListaCatalogoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListaCatalogosViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ListaCatalogosViewHolder,
        position: Int
    ) {
        holder.render(catalogosList[position], onItemSelected)
    }

    override fun getItemCount() = catalogosList.size
}