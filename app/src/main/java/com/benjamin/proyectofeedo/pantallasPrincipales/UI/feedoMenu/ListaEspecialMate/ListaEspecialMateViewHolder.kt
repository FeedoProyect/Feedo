package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.ListaEspecialMate

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemEspecialMateBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.squareup.picasso.Picasso

class ListaEspecialMateViewHolder(
    private val binding: ItemEspecialMateBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(comidasModel: ComidasSeccionMenuModel) {
        binding.tvEspecialMate.text = comidasModel.titulo
        Picasso.get().load(comidasModel.imagen).into(binding.imgEspecialMate)
    }
}
