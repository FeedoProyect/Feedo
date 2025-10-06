package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoBuscador.BuscadorPrincipalAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import io.github.jan.supabase.auth.Auth

class BuscadorPrincipalAdapter(
    private val auth: Auth,
    private var listComidas: List<ComidasModel> = emptyList(),
    private val onItemClick: (ComidasModel) -> Unit,
    private val onItemSelectedFav: (FavoritosRequestModel, Boolean) -> Unit
) : RecyclerView.Adapter<BuscadorPrincipalViewHolder>() {

    fun updateList(list: List<ComidasModel>) {
        listComidas = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuscadorPrincipalViewHolder {
        val binding = ItemSeccionesMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BuscadorPrincipalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuscadorPrincipalViewHolder, position: Int) {
        val item = listComidas[position]
        holder.render(item, auth, onItemClick, onItemSelectedFav)
    }

    override fun getItemCount() = listComidas.size
}

