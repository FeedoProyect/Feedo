package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.ListaEspecialMate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemEspecialMateBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel

class ListaEspecialMateAdapter(
    private var listaEspecialMate: List<ComidasSeccionMenuModel> = emptyList(),
    private val onItemClick: (ComidasSeccionMenuModel) -> Unit
) : RecyclerView.Adapter<ListaEspecialMateViewHolder>() {

    fun updateListEspecialMate(list: List<ComidasSeccionMenuModel>) {
        listaEspecialMate = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaEspecialMateViewHolder {
        val binding = ItemEspecialMateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListaEspecialMateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaEspecialMateViewHolder, position: Int) {
        val item = listaEspecialMate[position]
        holder.render(item)

        // 👇 click manejado acá
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = listaEspecialMate.size
}

