package com.benjamin.proyectofeedo.UI.feedoBuscador.BuscadorPrincipalAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidaBuscadorBinding
import com.benjamin.proyectofeedo.domain.model.ComidasModel

class BuscadorPrincipalAdapter(private var listComidaBuscador: List<ComidasModel> = emptyList()) :
    RecyclerView.Adapter<BuscadorPrincipalViewHolder>() {

    fun updateList(list: List<ComidasModel>){
        listComidaBuscador = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuscadorPrincipalViewHolder {
        val binding = ItemComidaBuscadorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuscadorPrincipalViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BuscadorPrincipalViewHolder,
        position: Int
    ) {
        holder.render(listComidaBuscador[position])
    }

    override fun getItemCount() = listComidaBuscador.size
}