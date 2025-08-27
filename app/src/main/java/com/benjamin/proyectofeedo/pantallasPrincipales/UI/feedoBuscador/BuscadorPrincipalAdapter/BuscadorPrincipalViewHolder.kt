package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoBuscador.BuscadorPrincipalAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidaBuscadorBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.squareup.picasso.Picasso

class BuscadorPrincipalViewHolder(private val binding: ItemComidaBuscadorBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(comidaBuscadorInfo: ComidasModel){
        binding.tvNombreComidaBuscador.text = comidaBuscadorInfo.titulo
        Picasso.get().load(comidaBuscadorInfo.imagen).into(binding.imgComidaBuscador)
    }
}