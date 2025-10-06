package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment.listaFavoritosAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemFavoritosPerfilBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosReceta
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import io.github.jan.supabase.auth.Auth

class ListaFavoritosAdapter(
    private var listFavoritos: MutableList<FavoritosReceta>,
    private val onDeleteClick: (FavoritosRequestModel) -> Unit,
    private val onItemClick: (FavoritosReceta) -> Unit,
    private val auth: Auth
) : RecyclerView.Adapter<ListaFavoritosViewHolder>() {

    fun updateListFavoritos(list: List<FavoritosReceta>) {
        listFavoritos.clear()
        listFavoritos.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(recetaId: Int) {
        val index = listFavoritos.indexOfFirst { it.id == recetaId }
        if (index != -1) {
            listFavoritos.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaFavoritosViewHolder {
        val binding = ItemFavoritosPerfilBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListaFavoritosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaFavoritosViewHolder, position: Int) {
        val item = listFavoritos[position]
        holder.render(item, auth) { favRequest, isFav ->
            if (!isFav) {
                // si lo desmarca, se borra de la lista
                onDeleteClick(favRequest)
                removeItem(item.id)
            }
        }
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount() = listFavoritos.size
}


