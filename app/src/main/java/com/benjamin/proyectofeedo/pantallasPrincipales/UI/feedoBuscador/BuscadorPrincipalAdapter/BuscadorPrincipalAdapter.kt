package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoBuscador.BuscadorPrincipalAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidaBuscadorBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import io.github.jan.supabase.auth.Auth

class BuscadorPrincipalAdapter(
    private val auth: Auth,
    private var listComidaBuscador: List<ComidasModel> = emptyList(),
    private val onItemClick: (ComidasModel) -> Unit,
    private val onItemSelectedFavs: (FavoritosRequestModel) -> Unit
) : RecyclerView.Adapter<BuscadorPrincipalViewHolder>() {

    fun updateList(list: List<ComidasModel>) {
        listComidaBuscador = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuscadorPrincipalViewHolder {
        val binding = ItemComidaBuscadorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BuscadorPrincipalViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BuscadorPrincipalViewHolder,
        position: Int
    ) {
        val item = listComidaBuscador[position]
        holder.render(item, onItemSelectedFavs, auth)

        // 👇 agregado
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = listComidaBuscador.size
}
