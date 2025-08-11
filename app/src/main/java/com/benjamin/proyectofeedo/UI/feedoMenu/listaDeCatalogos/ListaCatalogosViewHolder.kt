package com.benjamin.proyectofeedo.UI.feedoMenu.listaDeCatalogos

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.data.response.CatalogoResponse
import com.benjamin.proyectofeedo.databinding.ItemListaCatalogoBinding
import com.benjamin.proyectofeedo.domain.model.CatalogoInfo
import com.squareup.picasso.Picasso

class ListaCatalogosViewHolder(private val binding: ItemListaCatalogoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(catalogoInfo: CatalogoInfo, onItemSelected:(CatalogoInfo) -> Unit) {
        val context = binding.nameCatalogo.context
        binding.imCatalogo.setImageResource(catalogoInfo.img)
        binding.nameCatalogo.text = context.getString(catalogoInfo.titulo)

        binding.root.setOnClickListener { onItemSelected(catalogoInfo) }
    }
}