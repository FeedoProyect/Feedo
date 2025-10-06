package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaClasicosArgentinos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.holders.ListaClasicoArgentinoViewHolder
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import io.github.jan.supabase.auth.Auth

class ListaClasicoArgentinoAdapter(
    private val auth: Auth,
    private var listClasicoArgentino: List<ComidasSeccionMenuModel> = emptyList(),
    private val onItemClick: (ComidasSeccionMenuModel) -> Unit,
    private val onItemSelectedFavs: (FavoritosRequestModel, Boolean) -> Unit // 👈 importante: se recibe el estado
) : RecyclerView.Adapter<ListaClasicoArgentinoViewHolder>() {

    fun updateListClasicoArgentino(list: List<ComidasSeccionMenuModel>) {
        listClasicoArgentino = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaClasicoArgentinoViewHolder {
        val binding = ItemSeccionesMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListaClasicoArgentinoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaClasicoArgentinoViewHolder, position: Int) {
        val item = listClasicoArgentino[position]

        // 🔹 Renderiza el item con su estado actual de favorito
        holder.render(item, auth) { favorito, isFav ->
            onItemSelectedFavs(favorito, isFav)

            // 🔹 Actualiza solo el ítem tocado para mantener la UI consistente
            listClasicoArgentino[position].recetas.esFavorito = isFav
            notifyItemChanged(position)
        }

        // 🔹 Click general del ítem (para abrir detalle)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = listClasicoArgentino.size
}


