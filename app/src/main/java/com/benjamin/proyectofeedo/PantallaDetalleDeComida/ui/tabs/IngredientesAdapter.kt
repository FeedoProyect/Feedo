package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemIngredienteBinding
import com.benjamin.proyectofeedo.R


class IngredientesAdapter(
    private val data: List<String>
) : RecyclerView.Adapter<IngredientesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemIngredienteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngredienteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingrediente = data[position]
        holder.binding.txtTituloIngredientes.text = ingrediente

    }

    override fun getItemCount(): Int = data.size
}

