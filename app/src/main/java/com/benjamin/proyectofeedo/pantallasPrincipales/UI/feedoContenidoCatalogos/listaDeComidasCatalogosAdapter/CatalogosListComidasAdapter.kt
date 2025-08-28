package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoContenidoCatalogos.listaDeComidasCatalogosAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidasCatalogosBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel

class CatalogosListComidasAdapter(
    private var listComidas: List<ComidasModel> = emptyList(),
    private val onItemClick: (ComidasModel) -> Unit // 👈 callback
) : RecyclerView.Adapter<CatalogosListComidasViewHolder>() {

    fun updateList(list: List<ComidasModel>) {
        listComidas = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatalogosListComidasViewHolder {
        val binding = ItemComidasCatalogosBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CatalogosListComidasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogosListComidasViewHolder, position: Int) {
        val item = listComidas[position]
        holder.render(item)

        holder.itemView.setOnClickListener {
            onItemClick(item) // 👈 dispara el callback al hacer click
        }
    }

    override fun getItemCount() = listComidas.size
}
