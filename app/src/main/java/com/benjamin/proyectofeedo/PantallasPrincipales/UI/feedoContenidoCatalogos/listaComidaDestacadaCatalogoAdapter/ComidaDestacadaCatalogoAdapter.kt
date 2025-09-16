package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoContenidoCatalogos.listaComidaDestacadaCatalogoAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidaDestacadaCatalogoBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import io.github.jan.supabase.auth.Auth

class ComidaDestacadaCatalogoAdapter(
    private val auth: Auth,
    private var listComidaDestacadaCatalogo: List<ComidaDestacadaCatalogoModel> = emptyList(),
    private val onItemClick: ((ComidaDestacadaCatalogoModel) -> Unit)? = null,
    private val onItemSelectedFav: (FavoritosRequestModel) -> Unit
) : RecyclerView.Adapter<ComidaDestacadaCatalogoViewHolder>() {

    fun updateListComidaDestacadaCatalogo(list: List<ComidaDestacadaCatalogoModel>){
        listComidaDestacadaCatalogo = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComidaDestacadaCatalogoViewHolder {
        val binding = ItemComidaDestacadaCatalogoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComidaDestacadaCatalogoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ComidaDestacadaCatalogoViewHolder,
        position: Int
    ) {
        val comida = listComidaDestacadaCatalogo[position]
        holder.render(comida, onItemSelectedFav, auth)


        holder.itemView.setOnClickListener {
            onItemClick?.invoke(comida)
        }
    }

    override fun getItemCount() = listComidaDestacadaCatalogo.size
}
