package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoContenidoCatalogos.listaDeComidasCatalogosAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidasCatalogosBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import io.github.jan.supabase.auth.Auth

class CatalogosListComidasAdapter(
    private val auth: Auth, // 🔹 agregamos auth aquí
    private var listComidas: List<ComidasModel> = emptyList(),
    private val onItemClick: (ComidasModel) -> Unit,
    private val onItemSelectedFav: (FavoritosRequestModel) -> Unit
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
        holder.render(item, onItemSelectedFav, auth) // 🔹 pasamos auth
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }
    override fun getItemCount() = listComidas.size
}