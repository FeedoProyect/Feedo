package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.PantallaFotoIngredientes

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FotoIngredientesModel
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ItemFotoIngredienteBinding
import com.squareup.picasso.Picasso

class FtIngredienteViewHolder(private val binding: ItemFotoIngredienteBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(
        ingredientesModel: FotoIngredientesModel,
        onItemClick: (FotoIngredientesModel) -> Unit
    ) {
        binding.NombreIngrediente.text = ingredientesModel.nombre

        Picasso
            .get()
            .load(ingredientesModel.imagen)
            .error(R.drawable.img_error)
            .into(binding.imgIngredientes)

        binding.parent.setOnClickListener { onItemClick(ingredientesModel) }
    }
}