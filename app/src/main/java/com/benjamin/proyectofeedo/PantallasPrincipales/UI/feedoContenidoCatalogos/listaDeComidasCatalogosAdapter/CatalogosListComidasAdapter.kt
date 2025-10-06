package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoContenidoCatalogos.listaDeComidasCatalogosAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.holders.CatalogosListComidasViewHolder
import com.benjamin.proyectofeedo.databinding.ItemComidasCatalogosBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import io.github.jan.supabase.auth.Auth

class CatalogosListComidasAdapter(
    private val auth: Auth,
    private var listComidas: List<ComidasModel> = emptyList(),
    private val onItemClick: (ComidasModel) -> Unit,
    private val onItemSelectedFav: (FavoritosRequestModel, Boolean) -> Unit
) : RecyclerView.Adapter<CatalogosListComidasViewHolder>() {

    fun updateList(list: List<ComidasModel>) {
        listComidas = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatalogosListComidasViewHolder {
        val binding = ItemSeccionesMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )


        return CatalogosListComidasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogosListComidasViewHolder, position: Int) {
        val item = listComidas[position]
        holder.render(item, auth, onItemClick, onItemSelectedFav)
    }

    override fun getItemCount() = listComidas.size
}
