package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaModoSaludable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemModoSaludableBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import io.github.jan.supabase.auth.Auth

class ListaModoSaludableAdapter(
    private val auth: Auth,
    private var listaModoSaludable: List<ComidasSeccionMenuModel> = emptyList(),
    private val onItemClick: (ComidasSeccionMenuModel) -> Unit,
    private val onItemSelectedFavs: (FavoritosRequestModel) -> Unit
) : RecyclerView.Adapter<ListaModoSaludableViewHolder>() {

    fun updateListModoSaludable(list: List<ComidasSeccionMenuModel>) {
        listaModoSaludable = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaModoSaludableViewHolder {
        val binding = ItemModoSaludableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListaModoSaludableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaModoSaludableViewHolder, position: Int) {

        val item = listaModoSaludable[position]
        holder.render(item, onItemSelectedFavs, auth)

        // 👇 click desde el adapter
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = listaModoSaludable.size
}
