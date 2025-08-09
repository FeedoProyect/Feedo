package com.benjamin.proyectofeedo.UI.feedoMenu.listaDeCatalogos

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.data.CatalogosProvived
import com.benjamin.proyectofeedo.data.response.CatalogoResponse
import com.benjamin.proyectofeedo.data.response.CatalogosName
import com.benjamin.proyectofeedo.databinding.ItemListaCatalogoBinding
import com.squareup.picasso.Picasso

class listaCatalogosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemListaCatalogoBinding.bind(view)

    fun render(catalogosName: CatalogosName){
        binding.nameCatalogo.text = catalogosName.name
        Picasso.get().load(catalogosName.imageURL).into(binding.imCatalogo)
    }
}