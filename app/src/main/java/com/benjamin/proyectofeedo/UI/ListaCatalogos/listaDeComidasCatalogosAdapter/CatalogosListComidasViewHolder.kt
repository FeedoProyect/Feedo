package com.benjamin.proyectofeedo.UI.ListaCatalogos.listaDeComidasCatalogosAdapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.FragmentCatalogosListBinding
import com.benjamin.proyectofeedo.databinding.ItemComidasCatalogosBinding
import com.benjamin.proyectofeedo.domain.model.ComidasModel
import com.squareup.picasso.Picasso

class CatalogosListComidasViewHolder(private val binding: ItemComidasCatalogosBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(comidasModel: ComidasModel){
        binding.tvComidaName.text = comidasModel.titulo
        Picasso.get().load(comidasModel.imagen).into(binding.imgComidaCatalogo)
    }
}