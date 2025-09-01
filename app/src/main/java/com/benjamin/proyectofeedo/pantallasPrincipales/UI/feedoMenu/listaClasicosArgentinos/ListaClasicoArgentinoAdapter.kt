package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaClasicosArgentinos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemClasicoArgentinoBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import io.github.jan.supabase.auth.Auth

class ListaClasicoArgentinoAdapter(
    private val auth: Auth,
    private var listClasicoArgentino: List<ComidasSeccionMenuModel> = emptyList(),
    private val onItemClick: (ComidasSeccionMenuModel) -> Unit,
    private val onItemSelectedFavs: (FavoritosRequestModel) -> Unit
) : RecyclerView.Adapter<ListaClasicoArgentinoViewHolder>() {

    fun updateListClasicoArgentino(list: List<ComidasSeccionMenuModel>) {
        listClasicoArgentino = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaClasicoArgentinoViewHolder {
        val binding = ItemClasicoArgentinoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListaClasicoArgentinoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaClasicoArgentinoViewHolder, position: Int) {
        val item = listClasicoArgentino[position]
        holder.render(item, onItemSelectedFavs, auth)

        // 👇 Click lo manejamos aquí
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = listClasicoArgentino.size
}
