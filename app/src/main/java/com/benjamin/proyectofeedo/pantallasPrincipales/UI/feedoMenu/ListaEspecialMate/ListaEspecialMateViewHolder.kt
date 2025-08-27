package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.ListaEspecialMate

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemEspecialMateBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.squareup.picasso.Picasso

class ListaEspecialMateViewHolder(private val binding: ItemEspecialMateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(comidaSeccion: ComidasSeccionMenuModel) {
        binding.tvEspecialMate.text = comidaSeccion.titulo

        Picasso.get().load(comidaSeccion.imagen).into(binding.imgEspecialMate)
    }
}