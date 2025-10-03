package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.ListaAddIngredienteAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.IngredienteItem
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ItemAddIngredienteBinding
import com.squareup.picasso.Picasso

class AddIngredienteViewHolder(private val binding: ItemAddIngredienteBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(
        ingrediente: IngredienteItem,
        onItemDeleteClick: (IngredienteItem) -> Unit,
        onItemClick: (IngredienteItem) -> Unit
        ) {
        binding.tvAddIngrediente.text = ingrediente.nombre

        Picasso
            .get()
            .load(ingrediente.imagen)
            .error(R.drawable.img_error)
            .into(binding.imgAddIngredientes)

        binding.parent.setOnClickListener {
            onItemClick(ingrediente)
        }

        binding.imgDeleteIngrediente.setOnClickListener {
            onItemDeleteClick(ingrediente)
        }
    }
}