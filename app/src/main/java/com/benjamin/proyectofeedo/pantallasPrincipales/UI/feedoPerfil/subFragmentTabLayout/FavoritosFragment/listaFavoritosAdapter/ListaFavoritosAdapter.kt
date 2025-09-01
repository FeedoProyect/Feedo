package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment.listaFavoritosAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemFavoritosPerfilBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel

class ListaFavoritosAdapter(
    private var listFavoritos: MutableList<ComidasModel> = mutableListOf(),
    private val onDeleteClick: (ComidasModel) -> Unit
) :
    RecyclerView.Adapter<ListaFavoritosViewHolder>() {

    fun updateListFavoritos(list: List<ComidasModel>){
        listFavoritos.clear()
        listFavoritos.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(comida: ComidasModel) {
        val position = listFavoritos.indexOf(comida)
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
        holder.render(listFavoritos[position], onDeleteClick)
    }

    override fun getItemCount() = listFavoritos.size
}