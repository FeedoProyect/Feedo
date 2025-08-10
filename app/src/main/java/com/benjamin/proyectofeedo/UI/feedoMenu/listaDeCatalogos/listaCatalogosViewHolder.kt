package com.benjamin.proyectofeedo.UI.feedoMenu.listaDeCatalogos

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.data.CatalogosProvived
import com.benjamin.proyectofeedo.data.response.CatalogoResponse
import com.benjamin.proyectofeedo.data.response.CatalogosName
import com.benjamin.proyectofeedo.databinding.ItemListaCatalogoBinding
import com.squareup.picasso.Picasso

class listaCatalogosViewHolder(private val binding: ItemListaCatalogoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(catalogosResponse: CatalogoResponse) {
        binding.nameCatalogo.text = catalogosResponse.titulo
        Picasso.get().load(catalogosResponse.imagen).into(binding.imCatalogo)
    }
}