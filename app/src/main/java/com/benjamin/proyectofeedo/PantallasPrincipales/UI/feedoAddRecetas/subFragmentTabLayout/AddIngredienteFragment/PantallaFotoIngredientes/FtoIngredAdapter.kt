package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.PantallaFotoIngredientes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FotoIngredientesModel
import com.benjamin.proyectofeedo.databinding.ItemFotoIngredienteBinding

class FtIngredAdapter(
    private var listFotosIngredientes: List<FotoIngredientesModel> = emptyList(),
    private val onItemClick: (FotoIngredientesModel) -> Unit
) : RecyclerView.Adapter<FtIngredienteViewHolder>() {

    fun updateList(list: List<FotoIngredientesModel>) {
        listFotosIngredientes = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FtIngredienteViewHolder {
        val binding = ItemFotoIngredienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FtIngredienteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FtIngredienteViewHolder,
        position: Int
    ) {
        holder.render(listFotosIngredientes[position], onItemClick)
    }

    override fun getItemCount() = listFotosIngredientes.size
}