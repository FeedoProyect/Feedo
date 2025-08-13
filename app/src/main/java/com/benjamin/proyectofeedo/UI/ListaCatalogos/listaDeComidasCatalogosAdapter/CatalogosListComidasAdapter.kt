package com.benjamin.proyectofeedo.UI.ListaCatalogos.listaDeComidasCatalogosAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidasCatalogosBinding
import com.benjamin.proyectofeedo.domain.model.ComidasModel


class CatalogosListComidasAdapter(private var listComidas: List<ComidasModel> = emptyList()) :
    RecyclerView.Adapter<CatalogosListComidasViewHolder>() {

    fun updateList(list: List<ComidasModel>){
        listComidas = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatalogosListComidasViewHolder {
        val binding = ItemComidasCatalogosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogosListComidasViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CatalogosListComidasViewHolder,
        position: Int
    ) {
        holder.render(listComidas[position])
    }

    override fun getItemCount() = listComidas.size
}