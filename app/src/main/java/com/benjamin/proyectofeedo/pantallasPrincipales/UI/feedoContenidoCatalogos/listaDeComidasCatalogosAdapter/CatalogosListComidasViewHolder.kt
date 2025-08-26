package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoContenidoCatalogos.listaDeComidasCatalogosAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidasCatalogosBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.squareup.picasso.Picasso

class CatalogosListComidasViewHolder(private val binding: ItemComidasCatalogosBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(comidasModel: ComidasModel){
        binding.tvComidaName.text = comidasModel.titulo
        Picasso.get().load(comidasModel.imagen).into(binding.imgComidaCatalogo)
    }
}