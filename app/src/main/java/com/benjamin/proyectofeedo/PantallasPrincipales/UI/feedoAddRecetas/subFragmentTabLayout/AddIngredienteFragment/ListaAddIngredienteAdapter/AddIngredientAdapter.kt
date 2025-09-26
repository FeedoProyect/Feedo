package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.ListaAddIngredienteAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemAddIngredienteBinding

class AddIngredientAdapter : RecyclerView.Adapter<AddIngredienteViewHolder>() {

    private val listNewIngredient = mutableListOf<String>()

    fun addIngrediente(ingrediente: String){
        listNewIngredient.add(ingrediente)
        notifyItemInserted(listNewIngredient.size - 1)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddIngredienteViewHolder {
        val binding =
            ItemAddIngredienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddIngredienteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AddIngredienteViewHolder,
        position: Int
    ) {
        holder.render(listNewIngredient[position])
    }

    override fun getItemCount() = listNewIngredient.size
}