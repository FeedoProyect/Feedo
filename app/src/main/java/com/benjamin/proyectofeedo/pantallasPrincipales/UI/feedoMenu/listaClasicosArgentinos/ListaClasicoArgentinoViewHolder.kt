package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaClasicosArgentinos

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemClasicoArgentinoBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.squareup.picasso.Picasso

class ListaClasicoArgentinoViewHolder(
    private val binding: ItemClasicoArgentinoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(comidasModel: ComidasSeccionMenuModel) {
        binding.tvComidaClasicoArgentino.text = comidasModel.titulo

        Picasso.get().load(comidasModel.imagen).into(binding.imgComidaClasicoArgentino)
    }
}

