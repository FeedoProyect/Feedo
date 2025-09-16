package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaDeCatalogosAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemListaCatalogoBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.CatalogoInfo

class ListaCatalogosViewHolder(private val binding: ItemListaCatalogoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(catalogoInfo: CatalogoInfo, onItemSelected:(CatalogoInfo) -> Unit) {
        val context = binding.nameCatalogo.context
        binding.imCatalogo.setImageResource(catalogoInfo.img)
        binding.nameCatalogo.text = context.getString(catalogoInfo.titulo)

        binding.root.setOnClickListener { onItemSelected(catalogoInfo) }

    }
}