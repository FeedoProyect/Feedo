package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaClasicosArgentinos

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemClasicoArgentinoBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.squareup.picasso.Picasso

class ListaClasicoArgentinoViewHolder(private val binding: ItemClasicoArgentinoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(comidaSeccion: ComidasSeccionMenuModel){
        binding.tvComidaClasicoArgentino.text = comidaSeccion.titulo

        Picasso.get().load(comidaSeccion.imagen).into(binding.imgComidaClasicoArgentino)
    }
}