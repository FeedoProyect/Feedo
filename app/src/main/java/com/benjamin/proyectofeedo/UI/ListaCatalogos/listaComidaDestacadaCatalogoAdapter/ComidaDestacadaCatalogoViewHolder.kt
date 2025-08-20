package com.benjamin.proyectofeedo.UI.ListaCatalogos.listaComidaDestacadaCatalogoAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidaDestacadaCatalogoBinding
import com.benjamin.proyectofeedo.domain.model.ComidaDestacadaCatalogoModel
import com.squareup.picasso.Picasso

class ComidaDestacadaCatalogoViewHolder(private val binding: ItemComidaDestacadaCatalogoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(comidaDestacadaCatalogoInfo: ComidaDestacadaCatalogoModel) {
        binding.tvTituloComidaDestacadaCatalogo.text = comidaDestacadaCatalogoInfo.titulo
        binding.tvTiempoComidaDestacadaCatalogo.text = comidaDestacadaCatalogoInfo.tiempo

        Picasso.get().load(comidaDestacadaCatalogoInfo.imagen).into(binding.imgComidaDestacadaCatalogo)
    }
}