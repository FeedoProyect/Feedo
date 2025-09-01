package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaComidaExpres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemExpresBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import io.github.jan.supabase.auth.Auth

class ListaExpresAdapter(
    private val auth: Auth,
    private var listaExpres: List<ComidasSeccionMenuModel> = emptyList(),
    private val onItemClick: (ComidasSeccionMenuModel) -> Unit,
    private val onItemSelectedFavs: (FavoritosRequestModel) -> Unit
) : RecyclerView.Adapter<ListaExpresViewHolder>() {

    fun updateListExpres(list: List<ComidasSeccionMenuModel>) {
        listaExpres = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaExpresViewHolder {
        val binding = ItemExpresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListaExpresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaExpresViewHolder, position: Int) {
        val item = listaExpres[position]
        holder.render(item, onItemSelectedFavs, auth)

        // 👇 Click manejado acá
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = listaExpres.size
}
