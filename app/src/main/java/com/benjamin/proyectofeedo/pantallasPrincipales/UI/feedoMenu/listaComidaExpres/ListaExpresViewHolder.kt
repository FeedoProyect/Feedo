package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaComidaExpres

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemExpresBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.squareup.picasso.Picasso

class ListaExpresViewHolder(private val binding: ItemExpresBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(comidaModel: ComidasSeccionMenuModel){
        binding.tvExpres.text = comidaModel.titulo

        Picasso.get().load(comidaModel.imagen).into(binding.imgExpres)
    }
}