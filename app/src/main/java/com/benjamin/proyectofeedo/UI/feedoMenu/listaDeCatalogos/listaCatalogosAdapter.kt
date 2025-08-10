package com.benjamin.proyectofeedo.UI.feedoMenu.listaDeCatalogos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.data.response.CatalogoResponse
import com.benjamin.proyectofeedo.data.response.CatalogosName
import com.benjamin.proyectofeedo.databinding.ItemListaCatalogoBinding

class listaCatalogosAdapter(private val catalogosList: List<CatalogoResponse>) :
    RecyclerView.Adapter<listaCatalogosViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listaCatalogosViewHolder {
        val binding =
            ItemListaCatalogoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return listaCatalogosViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: listaCatalogosViewHolder,
        position: Int
    ) {
        holder.render(catalogosList[position])
    }

    override fun getItemCount() = catalogosList.size
}