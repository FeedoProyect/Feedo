package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.IngredienteModel
import com.benjamin.proyectofeedo.databinding.ItemIngredienteBinding
import com.squareup.picasso.Picasso

class IngredienteAdapter(
    private val ingredientes: List<IngredienteModel>
) : RecyclerView.Adapter<IngredienteAdapter.IngredienteViewHolder>() {

    inner class IngredienteViewHolder(val binding: ItemIngredienteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredienteViewHolder {
        val binding = ItemIngredienteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredienteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredienteViewHolder, position: Int) {
        val ingrediente = ingredientes[position]


        holder.binding.NombreIngrediente.text = ingrediente.nombre


        ingrediente.imagen?.let { url ->
            if (url.isNotEmpty()) {
                Picasso.get()
                    .load(url)
                    .placeholder(android.R.color.darker_gray) // mientras carga
                    .error(android.R.color.darker_gray)    // si np hay imagenn
                    .into(holder.binding.imgIngredientes)
            }
        }
    }

    override fun getItemCount(): Int = ingredientes.size
}


