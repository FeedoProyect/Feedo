package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.ListaModoAhorro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemModoAhorroBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import io.github.jan.supabase.auth.Auth

class ListaModoAhorroAdapter(
    private val auth: Auth,
    private var listModoAhorro: List<ComidasSeccionMenuModel> = emptyList(),
    private val onItemClick: (ComidasSeccionMenuModel) -> Unit,
    private val onItemSelectedFavs: (FavoritosRequestModel) -> Unit
) : RecyclerView.Adapter<ListaModoAhorroViewHolder>() {

    fun updateListModoAhorro(list: List<ComidasSeccionMenuModel>) {
        listModoAhorro = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaModoAhorroViewHolder {
        val binding = ItemModoAhorroBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListaModoAhorroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaModoAhorroViewHolder, position: Int) {
        val item = listModoAhorro[position]

        holder.render(item, onItemSelectedFavs, auth)

        // 👇 el click se maneja acá
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = listModoAhorro.size
}

