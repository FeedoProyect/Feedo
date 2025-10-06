package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaModoAhorro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import io.github.jan.supabase.auth.Auth

class ListaModoAhorroAdapter(
    private val auth: Auth,
    private var listaModoAhorro: List<ComidasSeccionMenuModel> = emptyList(),
    private val onItemClick: (ComidasSeccionMenuModel) -> Unit,
    private val onItemSelectedFavs: (FavoritosRequestModel, Boolean) -> Unit
) : RecyclerView.Adapter<ListaModoAhorroViewHolder>() {

    /** 🔁 Actualiza la lista completa */
    fun updateListModoAhorro(list: List<ComidasSeccionMenuModel>) {
        listaModoAhorro = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaModoAhorroViewHolder {
        val binding = ItemSeccionesMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListaModoAhorroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaModoAhorroViewHolder, position: Int) {
        val item = listaModoAhorro[position]

        // 🔹 Renderiza el ítem con animación + callback que devuelve estado
        holder.render(item, auth) { favorito, isFav ->
            onItemSelectedFavs(favorito, isFav)
            listaModoAhorro[position].recetas.esFavorito = isFav
            notifyItemChanged(position)
        }

        // 🔹 Click en el ítem completo (abre detalle)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = listaModoAhorro.size
}


