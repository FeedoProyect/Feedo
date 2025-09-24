package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaComidaExpres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
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
        val binding = ItemSeccionesMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
