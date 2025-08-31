package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.ListaModoAhorro

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemModoAhorroBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.squareup.picasso.Picasso

class ListaModoAhorroViewHolder(
    private val binding: ItemModoAhorroBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(comidasModel: ComidasSeccionMenuModel) {
        binding.tvModoAhorro.text = comidasModel.titulo
        Picasso.get().load(comidasModel.imagen).into(binding.imgModoAhorro)
    }
}
