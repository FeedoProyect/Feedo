package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaModoSaludable

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemModoSaludableBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.squareup.picasso.Picasso

class ListaModoSaludableViewHolder(private val binding: ItemModoSaludableBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(comidasModel: ComidasSeccionMenuModel){
        binding.tvModoSaludable.text = comidasModel.titulo

        Picasso.get().load(comidasModel.imagen).into(binding.imgModoSaludable)
    }
}