package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoContenidoCatalogos.listaComidaDestacadaCatalogoAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidaDestacadaCatalogoBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel

class ComidaDestacadaCatalogoAdapter(

    private var listComidaDestacadaCatalogo: List<ComidaDestacadaCatalogoModel> = emptyList(),

    // 👇 AGREGA ESTO: callback para el click
    private val onItemClick: ((ComidaDestacadaCatalogoModel) -> Unit)? = null
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
        holder.render(comida)

        // 👇 AGREGA ESTO: disparar el click
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(comida)
        }
    }

    override fun getItemCount() = listComidaDestacadaCatalogo.size
}
