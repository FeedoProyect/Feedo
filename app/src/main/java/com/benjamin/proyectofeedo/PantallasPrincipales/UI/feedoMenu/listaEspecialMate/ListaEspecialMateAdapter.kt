package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaEspecialMate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import io.github.jan.supabase.auth.Auth

class ListaEspecialMateAdapter(
    private val auth: Auth,
    private var listaEspecialMate: List<ComidasSeccionMenuModel> = emptyList(),
    private val onItemClick: (ComidasSeccionMenuModel) -> Unit,
    private val onItemSelectedFav: (FavoritosRequestModel) -> Unit
) : RecyclerView.Adapter<ListaEspecialMateViewHolder>() {

    fun updateListEspecialMate(list: List<ComidasSeccionMenuModel>) {
        listaEspecialMate = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaEspecialMateViewHolder {
        val binding = ItemSeccionesMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListaEspecialMateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaEspecialMateViewHolder, position: Int) {
        val item = listaEspecialMate[position]
        holder.render(item, onItemSelectedFav, auth)


        // 👇 click manejado acá
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = listaEspecialMate.size
}

