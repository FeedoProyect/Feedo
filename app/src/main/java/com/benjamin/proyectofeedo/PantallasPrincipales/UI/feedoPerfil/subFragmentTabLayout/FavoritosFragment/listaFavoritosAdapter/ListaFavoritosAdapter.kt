package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment.listaFavoritosAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemFavoritosPerfilBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosReceta
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import io.github.jan.supabase.auth.Auth

class ListaFavoritosAdapter(
    private var listFavoritos: MutableList<FavoritosReceta> = mutableListOf(),
    private val onDeleteClick: (FavoritosRequestModel) -> Unit,
    private val auth: Auth
) :
    RecyclerView.Adapter<ListaFavoritosViewHolder>() {

    fun updateListFavoritos(list: List<FavoritosReceta>){
        listFavoritos.clear()
        listFavoritos.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(recetaId: Int) {
        val position = listFavoritos.indexOfFirst { it.id == recetaId }
        if (position != -1) {
            listFavoritos.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaFavoritosViewHolder {
        val binding = ItemFavoritosPerfilBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListaFavoritosViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ListaFavoritosViewHolder,
        position: Int
    ) {
        holder.render(listFavoritos[position], onDeleteClick, auth)
    }

    override fun getItemCount() = listFavoritos.size
}