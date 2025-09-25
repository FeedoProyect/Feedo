package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.ListaAddIngredienteAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemAddIngredienteBinding

class AddIngredienteViewHolder(private val binding: ItemAddIngredienteBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(ingredientes: String){
        binding.tvListAddIngrediente.text = ingredientes
    }
}